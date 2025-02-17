package org.knowm.xchange.binance;

import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.account.futures.BinanceFutureAccountInformation;
import org.knowm.xchange.binance.dto.trade.*;
import org.knowm.xchange.binance.dto.trade.futures.BinanceFutureNewOrder;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface BinanceFuturesAuthenticated extends BinanceFutures{

    String SIGNATURE = "signature";
    String X_MBX_APIKEY = "X-MBX-APIKEY";

    /**
     * Get current futures account information.
     *
     * @param recvWindow optional
     * @param timestamp
     * @return
     * @throws IOException
     * @throws BinanceException
     */
    @GET
    @Path("fapi/v2/account")
    BinanceFutureAccountInformation futuresAccount(
            @QueryParam("recvWindow") Long recvWindow,
            @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
            @HeaderParam(X_MBX_APIKEY) String apiKey,
            @QueryParam(SIGNATURE) ParamsDigest signature)
            throws IOException, BinanceException;

    /**
     * Send in a new futures order
     *
     * @param symbol
     * @param side
     * @param type
     * @param timeInForce
     * @param quantity
     * @param price optional, must be provided for limit orders only
     * @param newClientOrderId optional, a unique id for the order. Automatically generated if not
     *     sent.
     * @param stopPrice optional, used with stop orders
     * @param newOrderRespType optional, MARKET and LIMIT order types default to FULL, all other
     *     orders default to ACK
     * @param recvWindow optional
     * @param timestamp
     * @return
     * @throws IOException
     * @throws BinanceException
     * @see <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">New order - Spot
     *     API docs -</a>
     */
    @POST
    @Path("fapi/v1/order")
    BinanceFutureNewOrder newOrder(
            @FormParam("symbol") String symbol,
            @FormParam("side") OrderSide side,
            @FormParam("type") OrderType type,
            @FormParam("timeInForce") TimeInForce timeInForce,
            @FormParam("quantity") BigDecimal quantity,
            @FormParam("reduceOnly") boolean reduceOnly,
            @FormParam("price") BigDecimal price,
            @FormParam("newClientOrderId") String newClientOrderId,
            @FormParam("stopPrice") BigDecimal stopPrice,
            @FormParam("closePosition") boolean closePosition,
            @FormParam("activationPrice") BigDecimal activationPrice,
            @FormParam("callbackRate") BigDecimal callbackRate,
            @FormParam("newOrderRespType") BinanceNewOrder.NewOrderResponseType newOrderRespType,
            @FormParam("recvWindow") Long recvWindow,
            @FormParam("timestamp") SynchronizedValueFactory<Long> timestamp,
            @HeaderParam(X_MBX_APIKEY) String apiKey,
            @QueryParam(SIGNATURE) ParamsDigest signature)
            throws IOException, BinanceException;

    /**
     * Cancel an active futures order.
     *
     * @param symbol
     * @param orderId optional
     * @param origClientOrderId optional
     *     generated by default.
     * @param recvWindow optional
     * @param timestamp
     * @param apiKey
     * @param signature
     * @return
     * @throws IOException
     * @throws BinanceException
     */
    @DELETE
    @Path("fapi/v1/order")
    BinanceCancelledOrder cancelFutureOrder(
            @QueryParam("symbol") String symbol,
            @QueryParam("orderId") long orderId,
            @QueryParam("origClientOrderId") String origClientOrderId,
            @QueryParam("recvWindow") Long recvWindow,
            @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
            @HeaderParam(X_MBX_APIKEY) String apiKey,
            @QueryParam(SIGNATURE) ParamsDigest signature)
            throws IOException, BinanceException;

    /**
     * Get future open orders on a symbol.
     *
     * @param symbol optional
     * @param recvWindow optional
     * @param timestamp
     * @return
     * @throws IOException
     * @throws BinanceException
     */
    @GET
    @Path("fapi/v1/openOrders")
    List<BinanceOrder> futureOpenOrders(
            @QueryParam("symbol") String symbol,
            @QueryParam("recvWindow") Long recvWindow,
            @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
            @HeaderParam(X_MBX_APIKEY) String apiKey,
            @QueryParam(SIGNATURE) ParamsDigest signature)
            throws IOException, BinanceException;

    /**
     * Get trades for a specific account and symbol.
     *
     * @param symbol
     * @param orderId optional
     * @param startTime optional
     * @param endTime optional
     * @param fromId optional, tradeId to fetch from. Default gets most recent trades.
     * @param limit optional, default 500; max 1000.
     * @param recvWindow optional
     * @param timestamp
     * @param apiKey
     * @param signature
     * @return
     * @throws IOException
     * @throws BinanceException
     */
    @GET
    @Path("fapi/v1/userTrades")
    List<BinanceTrade> myFutureTrades(
            @QueryParam("symbol") String symbol,
            @QueryParam("orderId") Long orderId,
            @QueryParam("startTime") Long startTime,
            @QueryParam("endTime") Long endTime,
            @QueryParam("fromId") Long fromId,
            @QueryParam("limit") Integer limit,
            @QueryParam("recvWindow") Long recvWindow,
            @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
            @HeaderParam(X_MBX_APIKEY) String apiKey,
            @QueryParam(SIGNATURE) ParamsDigest signature)
            throws IOException, BinanceException;

    /**
     * Check an order's status.<br>
     * Either orderId or origClientOrderId must be sent.
     *
     * @param symbol
     * @param orderId optional
     * @param origClientOrderId optional
     * @param recvWindow optional
     * @param timestamp
     * @param apiKey
     * @param signature
     * @return
     * @throws IOException
     * @throws BinanceException
     */
    @GET
    @Path("fapi/v1/order")
    BinanceOrder futureOrderStatus(
            @QueryParam("symbol") String symbol,
            @QueryParam("orderId") long orderId,
            @QueryParam("origClientOrderId") String origClientOrderId,
            @QueryParam("recvWindow") Long recvWindow,
            @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
            @HeaderParam(X_MBX_APIKEY) String apiKey,
            @QueryParam(SIGNATURE) ParamsDigest signature)
            throws IOException, BinanceException;

    /**
     * Cancels all active orders on a symbol. This includes OCO orders.
     *
     * @param symbol
     * @param recvWindow optional
     * @param timestamp
     * @return
     * @throws IOException
     * @throws BinanceException
     */
    @DELETE
    @Path("fapi/v1/allOpenOrders")
    List<BinanceCancelledOrder> cancelAllFutureOpenOrders(
            @QueryParam("symbol") String symbol,
            @QueryParam("recvWindow") Long recvWindow,
            @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
            @HeaderParam(X_MBX_APIKEY) String apiKey,
            @QueryParam(SIGNATURE) ParamsDigest signature)
            throws IOException, BinanceException;

}
