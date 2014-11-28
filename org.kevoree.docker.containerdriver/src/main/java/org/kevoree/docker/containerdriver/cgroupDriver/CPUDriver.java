package org.kevoree.docker.containerdriver.cgroupDriver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by aymeric on 28/11/14.
 */
public class CPUDriver {

    private static Double freq ;

    public static String getCpuValue(String containerId) {
        return   GenericDriver.ReadValue(containerId,CgroupStructure.cpuset_subsystem,CgroupStructure.cpuset_cpus) ;
    }

    public static String getFreqValue(String containerId) {
        if(freq == null)  {
            freq = getFreq();
        }
        String value  = GenericDriver.ReadValue(containerId,CgroupStructure.cpu_subsystem,CgroupStructure.cpu_cfs_quota) ;
        if(!value.isEmpty())   {
            Double time = Double.valueOf(value);
            Double cpu_req =  time * freq / 100000;
            return String.valueOf(cpu_req) ;
        }else
        {
        return  "" ;
        }
    }

    public static void setCPUValue(String containerId, String value) {
        GenericDriver.SetValue(containerId,CgroupStructure.cpuset_subsystem,CgroupStructure.cpuset_cpus,value) ;
    }

    public static void setFreqValue(String containerId, String value) {
        if(freq == null)  {
            freq = getFreq();
        }
            Double cpu_req = Double.parseDouble(value);

            // CGroup requires a time in micro second ;
            if(  cpu_req < freq ){
             Double time =  (cpu_req/freq) * 100000 ;
                GenericDriver.SetValue(containerId,CgroupStructure.cpu_subsystem,CgroupStructure.cpu_cfs_quota,String.valueOf(time.intValue())) ;
            }
    }


    private static double getFreq(){
        File cpuInfo = new File("/proc/cpuinfo") ;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(cpuInfo));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = null;
        String cpu_freq ="";
        boolean stop = false ;
        try {
            while ((line = br.readLine()) != null && !stop) {

                if (line.contains("cpu MHz")) {
                    stop = true ;
                    cpu_freq = line ;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        cpu_freq = cpu_freq.replaceAll("[^\\d.]", "");
        return  Double.parseDouble(cpu_freq);


    }

}
