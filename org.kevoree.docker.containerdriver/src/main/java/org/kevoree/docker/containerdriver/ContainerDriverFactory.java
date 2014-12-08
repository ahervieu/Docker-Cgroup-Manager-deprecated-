package org.kevoree.docker.containerdriver;

import org.kevoree.docker.containerdriver.cgroupDriver.NetworkDriver;
import org.kevoree.docker.containerdriver.model.Container;
import org.kevoree.docker.containerdriver.model.ContainerDetail;
import org.kevoree.docker.containerdriver.model.CustomContainerDetail;

/**
 * Created by aymeric on 05/12/14.
 */
public class ContainerDriverFactory {

    public CustomContainerDetail createCustomContainerDetail(ContainerDetail c){
        CustomContainerDetail currCD = new CustomContainerDetail(c) ;
     //   BridgeAcquisition ba = new BridgeAcquisition(currCD) ;
     //   Thread t = new Thread(ba);
     //   t.run();
        return currCD ;
    }

    class BridgeAcquisition implements Runnable{

        public BridgeAcquisition(CustomContainerDetail ccd)
        {
            this.ccd = ccd ;
        }

        private CustomContainerDetail ccd ;


        @Override
        public void run() {
            ccd.setBridge(NetworkDriver.getContainerBridge(ccd.getContainer().getNetworkSettings().getIpAddress()));
        }
    }

}

