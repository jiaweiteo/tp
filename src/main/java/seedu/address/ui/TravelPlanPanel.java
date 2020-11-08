package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.Directory;
import seedu.address.model.ObservableDirectory;


/**
 * Panel containing basic information of a Travel Plan.
 */
public class TravelPlanPanel extends UiPart<Region> {
    private static final String TravelPlanFXML = "TravelPlanPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TravelPlanPanel.class);

    private ObservableDirectory dir;

    private Logic logic;

    @FXML
    private Label name;
    @FXML
    private Label startDateToEndDate;
    @FXML
    private Label totalCost;

    /**
     * Creates a {@code TravelPlanObjectListPanel} with the given {@code ObservableList}.
     */
    public TravelPlanPanel(ObservableDirectory dir) {
        super(TravelPlanFXML);
        this.dir = dir;
        totalCost.textProperty().bind(dir.getObservableCost());
        update(dir.get().getValue());
    }

    /**
     * Updates the JavaFX properties of {@code TravelPlanPanel} according to the directory.
     */
    public void update(Directory directory) {
        name.setText(directory.getName().toString());

        if (directory.isTravelPlan()) {
            startDateToEndDate.setText(directory.getDateTitle());
        } else {
            startDateToEndDate.setText("");
        }
    }

}
