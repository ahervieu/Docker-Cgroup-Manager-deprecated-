package org.kevoree.docker.containerdriver.client.test;

import org.junit.Test;
import org.kevoree.docker.containerdriver.client.DockerClientImpl;
import org.kevoree.docker.containerdriver.model.Container;


import java.util.List;


public class DockerClientImplTest {

    @Test
    public void testGetContainers() throws Exception {
        DockerClientImpl dci = new DockerClientImpl() ;
        List<Container> lstCon = dci.getContainers() ;
        for (Container container : lstCon) {
            System.out.println(container.getNames()[0]);
        }
    }
}