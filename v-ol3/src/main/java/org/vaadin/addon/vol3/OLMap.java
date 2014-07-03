package org.vaadin.addon.vol3;

import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.Component;
import org.vaadin.addon.vol3.client.OLDeviceOptions;
import org.vaadin.addon.vol3.client.OLMapState;
import org.vaadin.addon.vol3.client.OLRendererType;
import org.vaadin.addon.vol3.layer.OLLayer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The core of the wrapper. Interact with this one to add OpenLayers 3 maps to your Vaadin application
 *
 * Created by mjhosio on 24/06/14.
 */
public class OLMap extends AbstractComponentContainer{
    private List<Component> components=new LinkedList<Component>();
    private OLView view;

    public OLMap(){
        this(null,null);
    }

    public OLMap(OLView view){
        this(view, null);
    }

    public OLMap(OLMapOptions options){
        this(null, options);
    }

    public OLMap(OLView view, OLMapOptions options){
        if(view!=null){
            setView(view);
        }
        if(options != null){
            setOptions(options);
        }
    }

    /** Adds a new layer to the map
     *
     * @param layer the layer to be added
     */
    public void addLayer(OLLayer layer){
        addComponent(layer);
    }

    /** Removes the specified layer from the map
     *
     * @param layer the layer to be removed
     */
    public void removeLayer(OLLayer layer){
        removeComponent(layer);
    }

    /** Sets the view of the map. The view can not be null
     *
     * @param view the view
     */
    public void setView(OLView view){
        if(view==null){
            throw new IllegalArgumentException("The view instance can not be null");
        }
        doSetView(view);
    }

    /** Gets the view associated with the map
     *
     * @return the view associated with the map
     */
    public OLView getView(){
        return view;
    }

    @Override
    public int getComponentCount() {
        return components.size();
    }

    @Override
    public Iterator<Component> iterator() {
        return components.iterator();
    }

    @Override
    protected OLMapState getState() {
        return (OLMapState) super.getState();
    }

    @Override
    protected OLMapState getState(boolean markAsDirty) {
        return (OLMapState) super.getState(markAsDirty);
    }

    @Override
    public void addComponent(Component c) {
        if(c instanceof OLLayer){
            super.addComponent(c);
            components.add(c);
        } else{
            throw new UnsupportedOperationException("Only instances of OLLayer can be added");
        }
    }

    @Override
    public void removeComponent(Component c) {
        if(c instanceof OLLayer){
            components.remove(c);
            super.removeComponent(c);
        }
    }

    private void doSetView(OLView olView) {
        if(olView==this.view){
            return;
        }
        if(this.view!=null){
            // remove old view
            components.remove(this.view);
            super.removeComponent(this.view);
        }
        this.view=olView;
        components.add(olView);
        super.addComponent(olView);
    }

    @Override
    public void replaceComponent(Component component, Component component2) {
        throw new UnsupportedOperationException("Replace component not implemented");
    }

    /** Returns true if the ol3 logo is shown
     *
     * @return
     */
    public Boolean getShowOl3Logo() {
        return getState(false).showOl3Logo;
    }

    /** Gets the renderer type used by the map
     *
     * @return
     */
    public OLRendererType getRenderer() {
        return getState(false).renderer;
    }

    /** Gets the device pixel ratio used by the map
     *
     * @return
     */
    public Double getPixelRatio() {
        return getState(false).pixelRatio;
    }

    /** Gets the device options used by the map
     *
     * @return
     */
    public OLDeviceOptions getDeviceOptions() {
        return getState(false).deviceOptions;
    }

    private void setOptions(OLMapOptions options) {
        getState().showOl3Logo=options.getShowOl3Logo();
        getState().renderer=options.getRenderer();
        getState().pixelRatio=options.getPixelRatio();
        getState().deviceOptions=options.getDeviceOptions();
    }
}