package ru.mephi.vikingdemo.service;
import org.springframework.stereotype.Service;
import ru.mephi.vikingdemo.controller.VikingListener;
import ru.mephi.vikingdemo.model.Viking;
import java.util.*;

@Service
public class VikingService {

    private final VikingFactory vikingFactory;
    private final Map<Integer, Viking> storage = new HashMap<>();
    private int nextId = 1;

    
    private VikingListener vikingListener;

    public VikingService(VikingFactory vikingFactory) {
        this.vikingFactory = vikingFactory;
    }

  
    public void setVikingListener(VikingListener vikingListener) {
        this.vikingListener = vikingListener;
    }

    public List<Viking> findAll() {
        return new ArrayList<>(storage.values());
    }

    public Viking addViking(Viking viking) {
        Viking withId = new Viking(
                nextId++,
                viking.name(),
                viking.age(),
                viking.heightCm(),
                viking.hairColor(),
                viking.beardStyle(),
                viking.equipment()
        );

        storage.put(withId.id(), withId);

        
        if (vikingListener != null) {
            vikingListener.onVikingCreated(withId);
        }

        return withId;
    }

    public Viking createRandomViking() {
        Viking v = vikingFactory.createRandomViking();

        Viking withId = new Viking(
                nextId++,
                v.name(),
                v.age(),
                v.heightCm(),
                v.hairColor(),
                v.beardStyle(),
                v.equipment()
        );

        storage.put(withId.id(), withId);

        
        if (vikingListener != null) {
            vikingListener.onVikingCreated(withId);
        }

        return withId;
    }

    public boolean deleteViking(int id) {
        boolean removed = storage.remove(id) != null;

        if (removed && vikingListener != null) {
            vikingListener.onVikingDeleted(id);
        }

        return removed;
    }

    public Viking updateViking(int id, Viking viking) {
        Viking updated = new Viking(
                id,
                viking.name(),
                viking.age(),
                viking.heightCm(),
                viking.hairColor(),
                viking.beardStyle(),
                viking.equipment()
        );

        storage.put(id, updated);

        if (vikingListener != null) {
            vikingListener.onVikingUpdated(updated);
        }

        return updated;
    }
}