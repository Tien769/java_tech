package ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import entity.Book;
import entity.OrderDetail;
import entity.Receipt;
import entity.User;

@Stateful
public class ShoppingCart implements ShoppingCartService {
    private Map<Book, Integer> cartItems;
    private int itemCount;
    private Receipt receipt;
    @EJB
    private DatabaseService db;

    public void initialize() {
        cartItems = new HashMap<Book, Integer>();
        receipt = new Receipt();
        itemCount = 0;
    }

    public void addBook(int bookId) {
        Book book = db.getBookById(bookId);
        if (book == null)
            return;
        cartItems.put(book, 1);
        updateItemCount();
    }

    public void removeBook(int bookId) {
        System.out.println(cartItems);
        cartItems.entrySet().removeIf(entry -> entry.getKey().getBookId() == bookId);
        System.out.println(cartItems);
        updateItemCount();
    }

    public void removeAll() {
        cartItems.clear();
    }

    public int getItemQuantity(Book book) {
        if (cartItems.containsKey(book)) {
            return cartItems.get(book);
        } else {
            return -1;
        }
    }

    public void setItemQuantity(Book book, Integer quantity) {
        cartItems.replace(book, quantity);
    }

    public double getTotal() {
        double total = 0;
        for (Book book : cartItems.keySet()) {
            double subtotal = book.getPrice().doubleValue() * cartItems.get(book).intValue();
            total += subtotal;
        }
        return total;
    }

    public User makeReceipt(User user, List<OrderDetail> details, Receipt receipt) {
        receipt.setOrderUser(user);
        for (OrderDetail o : details) {
            o.setDetailOrder(receipt);
            o.getOrderBook().hashCode();
            o.getDetailOrder().hashCode();
            receipt.addOrderDetail(o);
        }
        user.getUserReceipts().add(receipt);
        User updatedUser= db.updateAccount(user);
        return updatedUser;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public Map<Book, Integer> getCartItems() {
        return cartItems;
    }

    public int getItemCount() {
        updateItemCount();
        return itemCount;
    }

    private void updateItemCount() {
        if (cartItems == null | cartItems.size() == 0) {
            itemCount = 0;
            return;
        }
        itemCount = cartItems.size();
    }

    @Override
    public void setCartItems(Map<Book, Integer> cartItems) {
        this.cartItems = cartItems;

    }
}