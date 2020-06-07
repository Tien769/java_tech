package managedbean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import entity.BookCollection;

@Named
@RequestScoped
public class MenuViewModel {
    private MenuModel model;
    @Inject
    private UserPageBean userPageBean;
    private String newCollectionName;

    public MenuViewModel() {
        model = new DefaultMenuModel();
    }

    @PostConstruct
    public void init() {
        userPageBean.loadLibrary();
        // Library submenu
        DefaultSubMenu firstSubmenu = DefaultSubMenu.builder().label("Your Collection").build();
        for (BookCollection collection : userPageBean.getUserBookCollections()) {
            DefaultMenuItem item = DefaultMenuItem.builder().value(collection.getCollectionName())
                    .onclick("PF('collectionAction').show()")
                    .command(String.format("#{userPageBean.setReqCollectionId(%d)}", collection.getCollectionId()))
                    .build();
            firstSubmenu.getElements().add(item);
        }

        DefaultMenuItem createCollection = DefaultMenuItem.builder().value("new collection").icon("pi pi-plus")
                .onclick("PF('collectionNameInput').show()").build();
        model.getElements().add(firstSubmenu);
        model.getElements().add(createCollection);
    }

    public String createColletion() {
        userPageBean.createCollection(newCollectionName);
        return "list.xhtml?faces-redirect=true";
    }

    // ACCESSORS
    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }

    public String getNewCollectionName() {
        return newCollectionName;
    }

    public void setNewCollectionName(String newCollectionName) {
        this.newCollectionName = newCollectionName;
    }

}
