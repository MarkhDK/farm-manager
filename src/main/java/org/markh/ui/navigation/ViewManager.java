package org.markh.ui.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.markh.config.AppContext;
import org.markh.domain.model.Field;
import org.markh.persistence.ImplementRepository;
import org.markh.ui.controller.*;

public class ViewManager {
    private final AppContext context;
    private MainLayoutController mainController;

    public ViewManager(AppContext context) {
        this.context = context;
    }

    public Parent loadMainLayout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main_layout.fxml"));

            loader.setControllerFactory(type -> {
                try {
                    if (type == MainLayoutController.class) {
                        return new MainLayoutController(this);
                    }
                    if (type == DashboardController.class) {
                        return new DashboardController();
                    }
                    if (type == FieldsController.class) {
                        return new FieldsController(context.getFieldRepo(), context.getFieldService());
                    }
                    return type.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("ViewManager could not set controller factory: " + type, e);
                }
            });

            Parent root = loader.load();

            mainController = loader.getController();

            show("/views/dashboard.fxml");

            return root;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load main layout", e);
        }
    }

    public void show(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

            loader.setControllerFactory(type -> {
                try {
                    if (type == DashboardController.class) {
                        return new DashboardController();
                    }
                    if (type == FieldsController.class) {
                        return new FieldsController(context.getFieldRepo(), context.getFieldService());
                    }
                    if (type == PlansController.class) {
                        return new PlansController(context.getImplementRepo());
                    }
                    if (type == FarmController.class) {
                        return new FarmController(context.getImplementRepo(), context.getInputRepo(), context.getWorkTypeRepo());
                    }
                    return type.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            Parent view = loader.load();

            mainController.setContent(view);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load view: " + fxml, e);
        }
    }

    public void show(String fxml, Object data) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

            loader.setControllerFactory(type -> {
                try {
                    return type.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            Parent view = loader.load();

            Object controller = loader.getController();

            if (controller instanceof DataReceiver receiver) {
                receiver.setData(data);
            }

            mainController.setContent(view);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load view: " + fxml, e);
        }
    }
}
