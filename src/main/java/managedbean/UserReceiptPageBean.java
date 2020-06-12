package managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import entity.*;

@Named
@ViewScoped
public class UserReceiptPageBean extends BaseBean {
    private static final long serialVersionUID = -6708608590841958450L;
    private List<Receipt> receiptHistory;
    @Inject
    private UserPageBean userPage;

    // --------------------------------------CONSTRUCTOR--------------------------------------

    public UserReceiptPageBean() {
        receiptHistory = new ArrayList<Receipt>();
    }

    // --------------------------------------BEAN_FUNCTION--------------------------------------

    @PostConstruct
    public void initialize() {
        userPage.loadReceipt();
        this.receiptHistory = userPage.getUserReceipts();
    }

    public void viewReceipts() {
        for (Receipt entry : receiptHistory) {
            System.out.println("Date: " + entry.getDate());
            System.out.println("Items: ");
            for (OrderDetail detail : entry.getOrderDetails()) {
                System.out.println(String.format("Book: %1$s \tamount: %2$s", detail.getOrderBook().getTitle(),
                        detail.getAmount()));
            }
            System.out.println("Total: " + entry.getTotal());
            System.out.println("Payment method: " + entry.getPaymentMethod());
        }
    }

    // --------------------------------------ACCESSORS--------------------------------------
    public List<Receipt> getReceiptHistory() {
        return receiptHistory;
    }

    public void setReceiptHistory(List<Receipt> receiptHistory) {
        this.receiptHistory = receiptHistory;
    }

}