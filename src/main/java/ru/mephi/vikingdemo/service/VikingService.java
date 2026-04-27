package ru.mephi.vikingdemo.service;

import org.springframework.stereotype.Service;
import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.gui.VikingTableModel;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class VikingService {
    private final CopyOnWriteArrayList<Viking> vikings = new CopyOnWriteArrayList<>();
    private final VikingFactory vikingFactory;
    private VikingTableModel tableModel; 

    public VikingService(VikingFactory vikingFactory) {
        this.vikingFactory = vikingFactory;
    }

    public void setTableModel(VikingTableModel tableModel) {
        this.tableModel = tableModel;
    }
    
    public List<Viking> findAll() {
        return List.copyOf(vikings);
    }

    public Optional<Viking> findByName(String name) {
        return vikings.stream()
                .filter(v -> v.name().equalsIgnoreCase(name))
                .findFirst();
    }

    public Optional<Viking> findByIndex(int index) {
        if (index >= 0 && index < vikings.size()) {
            return Optional.of(vikings.get(index));
        }
        return Optional.empty();
    }

    public Viking createRandomViking() {
        Viking viking = vikingFactory.createRandomViking();
        vikings.add(viking);

        if (tableModel != null) {
            tableModel.addViking(viking);
        }
        
        return viking;
    }

    public Viking addViking(Viking viking) {
        vikings.add(viking);

        if (tableModel != null) {
            tableModel.addViking(viking);
        }
        
        return viking;
    }

    public boolean deleteByName(String name) {
        Optional<Viking> vikingToDelete = findByName(name);
        
        boolean removed = vikings.removeIf(v -> v.name().equalsIgnoreCase(name));

        if (removed && tableModel != null && vikingToDelete.isPresent()) {
            tableModel.removeViking(vikingToDelete.get());
        }
        
        return removed;
    }

    public boolean deleteByIndex(int index) {
        if (index < 0 || index >= vikings.size()) {
            return false;
        }
        
        Viking removedViking = vikings.remove(index);

        if (tableModel != null) {
            tableModel.removeViking(removedViking);
        }
        
        return true;
    }

    public boolean updateViking(String name, Viking newVikingData) {
        for (int i = 0; i < vikings.size(); i++) {
            if (vikings.get(i).name().equalsIgnoreCase(name)) {
                Viking oldViking = vikings.get(i);
                vikings.set(i, newVikingData);

                if (tableModel != null) {
                    tableModel.updateViking(oldViking, newVikingData);
                }
                
                return true;
            }
        }
        return false;
    }

    public boolean updateVikingByIndex(int index, Viking newVikingData) {
        if (index < 0 || index >= vikings.size()) {
            return false;
        }
        
        Viking oldViking = vikings.get(index);
        vikings.set(index, newVikingData);

        if (tableModel != null) {
            tableModel.updateViking(oldViking, newVikingData);
        }
        
        return true;
    }
}