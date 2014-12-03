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

        String value  = GenericDriver.ReadValue(containerId,CgroupStructure.cpu_subsystem,CgroupStructure.cpu_cfs_quota) ;
        if(value.contains("-1")){
            return value ;
        }else  if(!value.isEmpty())   {
            value = value.trim();
            int time = Integer.valueOf(value) ;
            if(time > 100){
                time = 100 ;
            }
            time = time/ 10000;
            return String.valueOf(time) ;
        }else
        {
        return  "" ;
        }
    }

    public static void setCPUValue(String containerId, String value) {
        GenericDriver.SetValue(containerId,CgroupStructure.cpuset_subsystem,CgroupStructure.cpuset_cpus,value) ;
    }

    public static void setFreqValue(String containerId, String value) {

        if(!value.isEmpty())   {
        int time = Integer.valueOf(value) * 10000 ;
            GenericDriver.SetValue(containerId,CgroupStructure.cpu_subsystem,CgroupStructure.cpu_cfs_period,"1000000") ;
        GenericDriver.SetValue(containerId,CgroupStructure.cpu_subsystem,CgroupStructure.cpu_cfs_quota,String.valueOf(time)) ;
        }
    }

}
