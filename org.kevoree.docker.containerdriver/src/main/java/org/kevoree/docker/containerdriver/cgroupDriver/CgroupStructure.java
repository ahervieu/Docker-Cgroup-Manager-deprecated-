package org.kevoree.docker.containerdriver.cgroupDriver;

/**
 * Created by aymeric on 26/11/14.
 */
public interface CgroupStructure {

    static final String cGroupURI              = "/sys/fs/cgroup/" ;
    //Subsystems
    static final String blkio_subsystem        = "blkio" ;
    static final String cpuset_subsystem       = "cpuset" ;
    static final String cpu_subsystem          = "cpu" ;
    static final String cpuacct_subsystem      = "cpuacct" ;
    static final String devices_subsystem      = "devices" ;
    static final String freezer_subsystem      = "freezer" ;
    static final String memory_subsystem       = "memory" ;
    static final String netcls_subsystem       = "netcls" ;


    //Files
    static final String blkio_write       = "blkio.throttle.write_bps_device" ;
    static final String blkio_read        = "blkio.throttle.read_bps_device" ;

    static final String memory_max_mem    = "memory.limit_in_bytes" ;
    static final String memory_swap       = "memory.memsw.limit_in_bytes" ;

}
