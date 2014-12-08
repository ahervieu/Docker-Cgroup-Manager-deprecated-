package org.kevoree.docker.containerdriver.controller;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.beans.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import org.kevoree.docker.containerdriver.ContainerDriverFactory;
import org.kevoree.docker.containerdriver.cgroupDriver.BlkioDriver;
import org.kevoree.docker.containerdriver.cgroupDriver.CPUDriver;
import org.kevoree.docker.containerdriver.cgroupDriver.MemoryDriver;
import org.kevoree.docker.containerdriver.cgroupDriver.NetworkDriver;
import org.kevoree.docker.containerdriver.client.DockerClientImpl;
import org.kevoree.docker.containerdriver.client.DockerException;
import org.kevoree.docker.containerdriver.model.Container;
import org.kevoree.docker.containerdriver.model.ContainerConfig;
import org.kevoree.docker.containerdriver.model.ContainerDetail;
import org.kevoree.docker.containerdriver.model.CustomContainerDetail;
import us.monoid.json.JSONException;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.Observable;
import java.util.stream.Stream;

/**
 * Created by aymeric on 25/11/14.
 */
public class ContainerDriverController implements Initializable {

    @FXML public TextField cpu_number;
    @FXML public TextField freq;

    @FXML public TextField maxMem;
    @FXML public TextField swap;

    @FXML public TextField incoming_traffic;
    @FXML public TextField outgoing_traffic;

    @FXML public TextField corrupt_rate;
    @FXML public TextField loss_rate;
    @FXML public TextField delay;

    @FXML public TextField io_read;
    @FXML public TextField io_write;

    @FXML private ListView<CustomContainerDetail> dockerContainers ;
    @FXML private TextField server;

    private ObservableList<CustomContainerDetail> CCD ;
   // private ObservableList<CustomContainerDetail> observableList ;

    private DockerClientImpl dci ;

    private ContainerDriverFactory factory  ;

    @FXML
    private void handleButtonApply() {
        if(true)
        {
        ContainerDetail  currContainer = getCurrentContainer();
            if(currContainer != null)
            {

        BlkioDriver.setWriteValue(currContainer.getId(),io_write.getText());
        BlkioDriver.setReadValue(currContainer.getId(),io_read.getText());

        CPUDriver.setCPUValue(currContainer.getId(),cpu_number.getText());
        CPUDriver.setFreqValue(currContainer.getId(), freq.getText());

        MemoryDriver.setMaxMemValue(currContainer.getId(), maxMem.getText());
        MemoryDriver.setSwapValue(currContainer.getId(), swap.getText());
        }
        }
    }

    private ContainerDetail getCurrentContainer()
    {

        ContainerDetail  currContainer = null ;
      
        try {
        if(dockerContainers.getSelectionModel().getSelectedItem() != null ){
       currContainer  =  dci.getContainer(dockerContainers.getSelectionModel().getSelectedItem().getContainer().getName());
        }

        } catch (DockerException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return currContainer ;
    }



    @FXML
    private void refreshServer() {
        dci = new DockerClientImpl(server.getText()) ;
        updateCustomContainerDetailList() ;
    }

    @FXML
    private void refreshServerKey() {
        dci = new DockerClientImpl(server.getText()) ;
        updateCustomContainerDetailList() ;
    }

    /*

    Check if there are new containers
    Check if some containers disapeard
    update the view
    handle selection
     */

    private void updateCustomContainerDetailList()
    {
        String currentSelectedContainer = "" ;
        if(dockerContainers.getSelectionModel().getSelectedItem() != null)
        {
            currentSelectedContainer = dockerContainers.getSelectionModel().getSelectedItem().getContainer().getName() ;
        }


        //Updating ContainerList
        // removing Container that are no more available
        try {

            List<Container> lstCon   = dci.getContainers();
           // Check that all current container in the app are still active

            for (CustomContainerDetail customContainerDetail : CCD) {
                boolean stop = false ;
                for (int i = 0; i < lstCon.size() && !stop ; i++) {
                    Container currContain = lstCon.get(i);
                    if(currContain.getId().equals(customContainerDetail.getContainer().getId()))
                    {
                        stop = true ;
                    }
                }
                if(!stop)  {
               //    CCD.remove(customContainerDetail);
                   CCD.remove(customContainerDetail) ;



            //        dockerContainers.

                }
            }

         // Check if there are new container
            // adding new container
            for (Container container : lstCon) {
                boolean found = false ;
                for (int i = 0; i < CCD.size() && !found; i++) {
                    CustomContainerDetail currContainDetail = CCD.get(i);
                    if(container.getId().equals(currContainDetail.getContainer().getId()))
                    {
                        found = true ;
                    }
                }
                if(!found)
                {
                    CCD.add(factory.createCustomContainerDetail(dci.getContainer(container.getId())));
                }
            }

            forceListRefreshOn(dockerContainers) ;
           // CCD.remove()  ;

            //Updating the view



            refreshContainerView() ;

        } catch (DockerException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void populateUI() {

        /*
    List<String> containerNameList = new ArrayList<String>();
    try {
        List<Container> lstCon   = dci.getContainers();
        for (Container container : lstCon) {
            containerNameList.add(container.getNames()[0].replace("/",""));
        }

    } catch (DockerException e) {
        System.err.println("Unable to connect to the rest server");
        e.printStackTrace();
    } catch (JSONException e) {

        System.err.println("Json Parsing Error");
        e.printStackTrace();
    }
*/
        //pdateCustomContainerDetailList() ;



    }

    private <T> void forceListRefreshOn(ListView<T> lsv) {
        ObservableList<T> items = lsv.<T>getItems();
        lsv.<T>setItems(null);
        lsv.<T>setItems(items);
    }



    private void refreshContainerView() {
        ContainerDetail currContainer = getCurrentContainer() ;
        if(currContainer != null) {
        ContainerConfig currConf = currContainer.getConfig();
        attachToolTip();
        io_read.setText(BlkioDriver.getReadValue(currContainer.getId()));
        io_write.setText(BlkioDriver.getWriteValue(currContainer.getId()));

        cpu_number.setText(CPUDriver.getCpuValue(currContainer.getId()));
        freq.setText(CPUDriver.getFreqValue(currContainer.getId()));

        maxMem.setText(MemoryDriver.getMaxMemValue(currContainer.getId()));
        swap.setText(MemoryDriver.getSwapValue(currContainer.getId()));


    }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        factory = new ContainerDriverFactory() ;
        if(!isRoot())
        {
            System.err.println("Root access is necessary to perform operations");
        }
     //   dockerContainers = new ListView<>();
        dci = new DockerClientImpl(server.getText()) ;
        CCD = FXCollections.observableArrayList();
        dockerContainers.setItems(CCD);
        if(!CCD.isEmpty())
        {
            dockerContainers.getSelectionModel().select(0);
        }

       dockerContainers.setCellFactory(new Callback<ListView<CustomContainerDetail>, ListCell<CustomContainerDetail>>() {
            @Override
            public ListCell<CustomContainerDetail> call(ListView<CustomContainerDetail> param) {
                ListCell<CustomContainerDetail> cell = new ListCell<CustomContainerDetail>() {
                    @Override
                    protected void updateItem(CustomContainerDetail t, boolean bln) {
                        super.updateItem(t, bln);

                        if (t != null && !bln) {
                           setText(t.nameProperty().getValue().replace("/",""));
                        }else{
                            setText(null);
                        }
                    }
                };

                return cell;
            }});

 /*       dockerContainers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomContainerDetail>() {
            @Override
            public void changed(ObservableValue<? extends CustomContainerDetail> observable, CustomContainerDetail oldValue, CustomContainerDetail newValue) {
                System.out.println(dockerContainers.getSelectionModel().getSelectedItem().nameProperty().getValue());
                updateCustomContainerDetailList() ;
            }
        }
        );*/
//        NetworkDriver.addIpTableRule() ;
        updateCustomContainerDetailList() ;




    }

    private void attachToolTip()
    {
        Tooltip io_wrt_tt = new Tooltip( );

        io_wrt_tt.setText(ToolTipDescriptor.blkio_throttle_write_descriptor);
        io_write.setTooltip(io_wrt_tt);

        Tooltip io_rd_tt = new Tooltip( );
        io_rd_tt.setText(ToolTipDescriptor.blkio_throttle_read_descriptor);
        io_read.setTooltip(io_rd_tt);

        Tooltip cpu_number_tt = new Tooltip( );
        cpu_number_tt.setText(ToolTipDescriptor.cpuset_cpus_descriptor);
        cpu_number.setTooltip(cpu_number_tt);

        Tooltip freq_tt = new Tooltip( );
        freq_tt.setText(ToolTipDescriptor.cpf_quota_us_descriptor);
        freq.setTooltip(freq_tt);

        Tooltip maxMem_tt = new Tooltip( );
        maxMem_tt.setText(ToolTipDescriptor.memory_max_mem_descriptor);
        maxMem.setTooltip(maxMem_tt);

        Tooltip swap_tt = new Tooltip( );
        swap_tt.setText(ToolTipDescriptor.memory_swap_descriptor);
        swap.setTooltip(swap_tt);


    }

    private boolean isRoot()
    {
        return  new File("/usr/bin/ls").canWrite();
    }
   }
