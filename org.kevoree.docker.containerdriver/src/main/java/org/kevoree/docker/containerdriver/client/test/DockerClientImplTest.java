package org.kevoree.docker.containerdriver.client.test;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.junit.Test;
import org.kevoree.docker.containerdriver.client.DockerClientImpl;
import org.kevoree.docker.containerdriver.model.Container;
import org.kevoree.docker.containerdriver.model.ExecConfig;


import java.util.List;


public class DockerClientImplTest {

    @Test
    public void testGetContainers() throws Exception {
        DockerClientImpl dci = new DockerClientImpl() ;
        List<Container> lstCon = dci.getContainers() ;
        String[] tab= {"mkdir  jjjj"};
        ExecConfig execConf = new ExecConfig(true,true,true,true,tab,lstCon.get(0).getId());
        dci.exec(lstCon.get(0).getId(),execConf);
        System.out.println(lstCon.get(0).getNames()[0]);

    }
}