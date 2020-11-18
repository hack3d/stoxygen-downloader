package de.stoxygen.alphavantage;

import de.stoxygen.model.alphavantage.StockUnit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class StockUnitTest {

    @Test
    public void testStockUnit() {
        StockUnit stockUnit = new StockUnit();
        stockUnit.setOpen(1.0);
        stockUnit.setClose(2.0);
        stockUnit.setHigh(3.0);
        stockUnit.setLow(4.0);
        stockUnit.setVolume(100l);
        stockUnit.setDateTime("2020-01-01");

        assertEquals(stockUnit.getOpen(), 1.0, 0.0);
        assertEquals(stockUnit.getClose(), 2.0, 0.0);
        assertEquals(stockUnit.getHigh(), 3.0, 0.0);
        assertEquals(stockUnit.getLow(), 4.0, 0.0);
        assertEquals(stockUnit.getVolume(), 100l);
        assertEquals(stockUnit.getDateTime(), "2020-01-01");
        assertNotNull(stockUnit.toString());
    }
}
