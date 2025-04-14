package com.spacewars.control;

import java.util.HashMap;
import java.util.Set;

public class MVCLinkMap<M, V> {
    private HashMap<M, V> modelToView = new HashMap<>();
    private HashMap<V, M> viewToModel = new HashMap<>();

    public void put(M model, V view) {
        modelToView.put(model, view);
        viewToModel.put(view, model);
    }

    public boolean removeModel(M toUnlink) {
        if(modelToView.containsKey(toUnlink)) {
            V view = modelToView.get(toUnlink);

            modelToView.remove(toUnlink);
            viewToModel.remove(view);
            return true;
        }
        else return false;
    }

    public boolean removeView(V toUnlink) {
        if(viewToModel.containsKey(toUnlink)) {
            M model = viewToModel.get(toUnlink);

            viewToModel.remove(toUnlink);
            modelToView.remove(model);
            return true;
        }
        else return false;
    }

    public boolean containsModel(M toCheckLink) {
        return modelToView.containsKey(toCheckLink);
    }

    public boolean containsView(V toCheckLink) {
        return viewToModel.containsKey(toCheckLink);
    }

    public V getView(M model) {
        return modelToView.getOrDefault(model, null);
    }

    public M getModel(V view) {
        return viewToModel.getOrDefault(view, null);
    }

    public Set<M> getModelSet() {
        return modelToView.keySet();
    }

    public Set<V> getViewSet() {
        return  viewToModel.keySet();
    }

    public void clear() {
        modelToView.clear();
        viewToModel.clear();
    }
}
