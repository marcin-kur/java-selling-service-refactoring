package uj.jwzp.w2.e3;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.math.BigDecimal;

public class OrderServiceTest {

    @Mock
    private DiscountsConfiguration discountsConfiguration;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void calculatePriceForExpensiveItemDuringWeekend() {
        // given
        Mockito.when(discountsConfiguration.isWeekendPromotion()).thenReturn(Boolean.TRUE);
        Mockito.when(discountsConfiguration.getDiscountForItem(Mockito.any(), Mockito.any())).thenReturn(BigDecimal.ZERO);
        OrderService orderService = new OrderService(discountsConfiguration);
        Item i = new Item("i", BigDecimal.TEN);
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
        Order order = new Order(i, 1, c);

        //when
        BigDecimal price = orderService.calculatePrice(order);

        //then
        Assert.assertEquals(BigDecimal.valueOf(7), price);
    }

    @Test
    public void calculatePriceForCheapItemDuringWeekend() {
        // given
        Mockito.when(discountsConfiguration.isWeekendPromotion()).thenReturn(Boolean.TRUE);
        Mockito.when(discountsConfiguration.getDiscountForItem(Mockito.any(), Mockito.any())).thenReturn(BigDecimal.ZERO);
        OrderService orderService = new OrderService(discountsConfiguration);
        Item i = new Item("i", BigDecimal.ONE);
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
        Order order = new Order(i, 1, c);

        //when
        BigDecimal price = orderService.calculatePrice(order);

        //then
        Assert.assertEquals(BigDecimal.ONE, price);
    }

    @Test
    public void calculatePriceForExpensiveItemDuringWorkweek() {
        // given
        Mockito.when(discountsConfiguration.isWeekendPromotion()).thenReturn(Boolean.FALSE);
        Mockito.when(discountsConfiguration.getDiscountForItem(Mockito.any(), Mockito.any())).thenReturn(BigDecimal.ZERO);
        OrderService orderService = new OrderService(discountsConfiguration);
        Item i = new Item("i", BigDecimal.TEN);
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
        Order order = new Order(i, 1, c);

        //when
        BigDecimal price = orderService.calculatePrice(order);

        //then
        Assert.assertEquals(BigDecimal.valueOf(10), price);
    }

    @Test
    public void calculatePriceForCheapItemDuringWorkweek() {
        // given
        Mockito.when(discountsConfiguration.isWeekendPromotion()).thenReturn(Boolean.FALSE);
        Mockito.when(discountsConfiguration.getDiscountForItem(Mockito.any(), Mockito.any())).thenReturn(BigDecimal.ZERO);
        OrderService orderService = new OrderService(discountsConfiguration);
        Item i = new Item("i", BigDecimal.ONE);
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
        Order order = new Order(i, 1, c);

        //when
        BigDecimal price = orderService.calculatePrice(order);

        //then
        Assert.assertEquals(BigDecimal.ONE, price);
    }
}
