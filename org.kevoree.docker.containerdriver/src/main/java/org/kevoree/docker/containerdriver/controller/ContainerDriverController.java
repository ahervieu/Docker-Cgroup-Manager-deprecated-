package org.kevoree.docker.containerdriver.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import org.kevoree.docker.containerdriver.cgroupDriver.BlkioDriver;
import org.kevoree.docker.containerdriver.cgroupDriver.CPUDriver;
import org.kevoree.docker.containerdriver.cgroupDriver.MemoryDriver;
import org.kevoree.docker.containerdriver.cgroupDriver.NetworkDriver;
import org.kevoree.docker.containerdriver.client.DockerClientImpl;
import org.kevoree.docker.containerdriver.client.DockerException;
import org.kevoree.docker.containerdriver.model.Container;
import org.kevoree.docker.containerdriver.model.ContainerConfig;
import org.kevoree.docker.containerdriver.model.ContainerDetail;
import us.monoid.json.JSONException;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

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

    @FXML private ListView<String> dockerContainers ;
    @FXML private TextField server;

///    private LinkedList
    private DockerClientImpl dci ;

    @FXML
    private void handleButtonApply() {
        if(true)
        {
        ContainerDetail  currContainer = getCurrentContainer();

        BlkioDriver.setWriteValue(currContainer.getId(),io_write.getText());
        BlkioDriver.setReadValue(currContainer.getId(),io_read.getText());

        CPUDriver.setCPUValue(currContainer.getId(),cpu_number.getText());
        CPUDriver.setFreqValue(currContainer.getId(), freq.getText());

        MemoryDriver.setMaxMemValue(currContainer.getId(), maxMem.getText());
        MemoryDriver.setSwapValue(currContainer.getId(), swap.getText());
        }
    }

    private ContainerDetail getCurrentContainer()
    {

        ContainerDetail  currContainer = null ;
        try {
            currContainer  =  dci.getContainer(dockerContainers.getSelectionModel().getSelectedItem());
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
        populateUI() ;
    }

    @FXML
    private void refreshServerKey() {
        dci = new DockerClientImpl(server.getText()) ;
        populateUI() ;
    }


    private void populateUI() {
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

    ObservableList<String> observableList = FXCollections.observableArrayList(containerNameList);
    dockerContainers.setItems(observableList);
    if(!dockerContainers.getItems().isEmpty())
    {
        dockerContainers.getSelectionModel().select(0);

        refreshContainerView() ;
    }
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

        }else{

            populateUI() ;
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    //    NetworkDriver.addIpTableRule() ;
        if(!isRoot())
        {
            System.err.println("Root access is necessary to perform operations");
        }

        dci = new DockerClientImpl(server.getText()) ;

        dockerContainers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                refreshContainerView() ;
            }
        });
        populateUI();
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
