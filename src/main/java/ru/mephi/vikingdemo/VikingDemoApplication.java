package ru.mephi.vikingdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.mephi.vikingdemo.gui.VikingDesktopFrame;
import ru.mephi.vikingdemo.gui.VikingTableModel;

import javax.swing.SwingUtilities;
import ru.mephi.vikingdemo.controller.VikingListener;
import ru.mephi.vikingdemo.service.VikingService;

@SpringBootApplication
public class VikingDemoApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(VikingDemoApplication.class);
        app.setHeadless(false);

        ConfigurableApplicationContext context = app.run(args);

        VikingService vikingService = context.getBean(VikingService.class);
        VikingListener vikingListener = context.getBean(VikingListener.class);
        
        VikingTableModel tableModel = new VikingTableModel();
        
        vikingService.setTableModel(tableModel);
        
        SwingUtilities.invokeLater(() -> {
            VikingDesktopFrame frame = new VikingDesktopFrame(vikingService, tableModel);
            vikingListener.setGui(frame);
            frame.setVisible(true);
        });
    }
}