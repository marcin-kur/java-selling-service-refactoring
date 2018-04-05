package uj.jwzp.w2.e3;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;

public class CustomerMoneyServiceTest {

    @Mock
    private PersistenceLayer persistenceLayer;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void getMoneyForNewCustomer() {
        // given
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        CustomerMoneyService customerMoneyService = new CustomerMoneyService(persistenceLayer);
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");

        // when
        BigDecimal money = customerMoneyService.getMoney(c);

        // then
        Assert.assertEquals(BigDecimal.TEN, money);
    }

    @Test
    public void addMoney() {
        // given
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        CustomerMoneyService customerMoneyService = new CustomerMoneyService(persistenceLayer);
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");

        // when
        customerMoneyService.addMoney(c, BigDecimal.TEN);

        // then
        Assert.assertEquals(BigDecimal.valueOf(20), customerMoneyService.getMoney(c));
    }

    @Test
    public void paymentSuccessful() {
        // given
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        CustomerMoneyService customerMoneyService = new CustomerMoneyService(persistenceLayer);
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");

        // when
        boolean paymentResult = customerMoneyService.pay(c, BigDecimal.ONE);

        // then
        Assert.assertTrue(paymentResult);
        Assert.assertEquals(BigDecimal.valueOf(9), customerMoneyService.getMoney(c));
    }

    @Test
    public void paymentFailed() {
        // given
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        CustomerMoneyService customerMoneyService = new CustomerMoneyService(persistenceLayer);
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");

        // when
        boolean paymentResult = customerMoneyService.pay(c, BigDecimal.valueOf(20));

        // then
        Assert.assertFalse(paymentResult);
        Assert.assertEquals(BigDecimal.TEN, customerMoneyService.getMoney(c));
    }
}
