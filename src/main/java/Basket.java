import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Класс, описывающий покупательскую корзину.
 */

public class Basket implements Serializable {

    private String[] productsNames;
    private int[] productsCount;
    private int[] prices;

    public Basket(String[] productsNames, int[] prices) {
        this.productsNames = productsNames;
        this.prices = prices;
        this.productsCount = new int[productsNames.length];
    }

    public Basket() {
    }

    public String[] getProductsNames() {
        return productsNames;
    }

    public void setProductsNames(String[] productsNames) {
        this.productsNames = productsNames;
    }

    public int[] getProductsCount() {
        return productsCount;
    }

    public int[] getPrices() {
        return prices;
    }

    public void setPrices(int[] prices) {
        this.prices = prices;
    }

    public void setProductsCount(int[] productsCount) {
        this.productsCount = productsCount;
    }

    /**
     * Выводит на экран весь ассортимент продуктов для покупки.
     */
    protected void printListAllProductsForBuy() {
        System.out.println("Список возможных товаров для покупки: ");
        for (int i = 0; i < productsNames.length; i++) {
            System.out.println((i + 1) + ". " + productsNames[i] + " " + prices[i] + " руб/шт.");
        }
    }

    /**
     * Добавляет кол-во продуктов по номеру.
     *
     * @param productNum номер продукта из списка.
     * @param amount     кол-во продукта из списка.
     */
    protected void addToCart(int productNum, int amount) {
        productsCount[productNum] += amount;
    }

    /**
     * Выводит на экран покупательскую корзину.
     */
    protected void printCart() {
        System.out.println("Ваша корзина:");
        int sum = 0;
        for (int i = 0; i < productsCount.length; i++) {
            int allCountProduct = productsCount[i];
            int priceSumByProduct = prices[i] * allCountProduct;
            if (allCountProduct > 0) {
                System.out.println(
                        productsNames[i] + " " + allCountProduct + " шт. в сумме " + priceSumByProduct
                        + " руб.");
                sum += priceSumByProduct;
            }
        }
        System.out.println("Итого: " + sum + " руб.");
    }

    /**
     * Сохраняет объект корзины в формат JSON.
     *
     * @param jsonFile файл формата JSON.
     */
    public void saveJson(File jsonFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode parent = objectMapper.createObjectNode();

        ArrayNode arrayNodeNames = objectMapper.createArrayNode();
        for (String productsName : productsNames) {
            arrayNodeNames.add(productsName);
        }
        parent.set("productsNames", arrayNodeNames);

        ArrayNode arrayNodeCount = objectMapper.createArrayNode();
        for (int j : productsCount) {
            arrayNodeCount.add(j);
        }
        parent.set("productsCount", arrayNodeCount);

        ArrayNode arrayNodePrice = objectMapper.createArrayNode();
        for (int price : prices) {
            arrayNodePrice.add(price);
        }
        parent.set("prices", arrayNodePrice);

        objectMapper.writeValue(jsonFile, parent);
    }

    /**
     * Восстанавливает объект корзины из JSON файла, в который ранее она была сохранена.
     *
     * @param jsonFile файл формата JSON.
     * @throws IOException
     */
    public static Basket loadFromJsonFile(File jsonFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Basket basket = objectMapper.readValue(jsonFile, Basket.class);
        System.out.print("Корзина восстановлена!:" + "\n");
        basket.printCart();
        return basket;
    }

    @Override
    public String toString() {
        return "Basket{" +
               "productsNames=" + Arrays.toString(productsNames) +
               ", productsCount=" + Arrays.toString(productsCount) +
               ", prices=" + Arrays.toString(prices) +
               '}';
    }
}