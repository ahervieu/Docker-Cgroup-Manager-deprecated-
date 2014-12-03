package org.kevoree.docker.containerdriver.model;

import org.kevoree.docker.containerdriver.cgroupDriver.NetworkDriver;

/**
 * Created by aymeric on 02/12/14.
 */
public class CustomContainerDetail {


    private ContainerDetail container ;
    private String bridge ;
    private int incomingTraffic ;
    private int outgoingTraffic ;
    private int corruptionRate ;
    private int lossRate ;
    private int delayRate ;
    private Thread t ;

    public CustomContainerDetail(ContainerDetail container) {
        this.container = container;
        BridgeAcquisition ba = new BridgeAcquisition() ;
        t = new Thread(ba);
    }



    public ContainerDetail getContainer() {
        return container;
    }

    public void setContainer(ContainerDetail container) {
        this.container = container;
    }

    public String getBridge() {
        if(!t.isAlive()) {
        return bridge;
        }else{
            return "" ;
        }

    }

    private void setBridge() {

    }

    public int getIncomingTraffic() {
        return incomingTraffic;
    }

    public void setIncomingTraffic(int incomingTraffic) {
        this.incomingTraffic = incomingTraffic;
    }

    public int getOutgoingTraffic() {
        return outgoingTraffic;
    }

    public void setOutgoingTraffic(int outgoingTraffic) {
        this.outgoingTraffic = outgoingTraffic;
    }

    public int getCorruptionRate() {
        return corruptionRate;
    }

    public void setCorruptionRate(int corruptionRate) {
        this.corruptionRate = corruptionRate;
    }

    public int getLossRate() {
        return lossRate;
    }

    public void setLossRate(int lossRate) {
        this.lossRate = lossRate;
    }

    public int getDelayRate() {
        return delayRate;
    }

    public void setDelayRate(int delayRate) {
        this.delayRate = delayRate;
    }

    class BridgeAcquisition implements Runnable{

       // private String bridge ;


        @Override
        public void run() {
            bridge =  NetworkDriver.getContainerBridge(container.getNetworkSettings().getIpAddress());
        }
    }
}
