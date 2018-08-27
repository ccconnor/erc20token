package io.imapp.test;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.bouncycastle.util.Strings;
import org.web3j.abi.EventValues;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;

import io.imapp.test.StandardToken.TransferEventResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	Web3j web3 = Web3j.build(new HttpService("https://ropsten.infura.io/v3/d70de84d1eb74dfba5f4b3280f09bbcc"));
    	String contractAddress = "0x62A25206ac19E2467871F8C4b2566c464D98041d";
    	String accountAddress = "0xa5433a9c6f65ffefd1f8edc7970216edcbce5ac3";
    	String toAddress = "0xfa10329a8b2326476e93eb60cab21c69fe4130d6";
    	String privateKey = "E523E8E83EEEDD55A775B0FEBAB422E5AFE02EC3C225F71DA259E0349428854D";
    	MyToken token = new MyToken(web3, contractAddress, accountAddress, privateKey);

    	System.out.printf("name: %s\n", token.name());
    	System.out.printf("symbol: %s\n", token.symbol());
    	System.out.printf("decimals: %s\n", token.decimals().toString());
    	System.out.printf("totalSupply: %s\n", token.totalSupply().toString());
    	System.out.printf("owner: %s\n", token.owner());
    	System.out.printf("balance of [%s]: %s\n", accountAddress, token.balanceOf(accountAddress));
    	System.out.printf("[%s] frozen? %s\n", accountAddress, token.frozenAccount(accountAddress) ? "true" : "false");
    	System.out.printf("[%s] allow [%s] spend %s tokens\n", toAddress, accountAddress, token.allowance(toAddress, accountAddress));

    	String txHash;
//    	txHash = token.transfer(toAddress, BigInteger.valueOf(1000L));
//    	System.out.println(txHash);

//    	txHash = token.mint(accountAddress, BigInteger.valueOf(1000L));
//    	System.out.println(txHash);
    	
    	Map<String, BigInteger> valueList = new HashMap<String, BigInteger>();
    	valueList.put("0xfa10329a8b2326476e93eb60cab21c69fe4130d6", BigInteger.valueOf(1000L));
    	txHash = token.distribute(valueList);
    	System.out.println(txHash);
    }
    
    public static String sendSomething(String yourData) throws InterruptedException, ExecutionException
    {
    	// There are no local accounts in INFURA, therefore the following methods are disabled:
    	// eth_coinbase
    	// eth_accounts
    	// eth_sign
    	// eth_sendTransaction
    	Web3j web3 = Web3j.build(new HttpService("https://ropsten.infura.io/v3/d70de84d1eb74dfba5f4b3280f09bbcc"));

    	String address = "0xa5433a9c6f65ffefd1f8edc7970216edcbce5ac3";
    	EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).sendAsync().get();
    	BigInteger nonce = ethGetTransactionCount.getTransactionCount();
    	BigInteger gasPrice = DefaultGasProvider.GAS_PRICE;
    	BigInteger gasLimit = DefaultGasProvider.GAS_LIMIT;
    	String data = Numeric.toHexString(Strings.toUTF8ByteArray(yourData));
    	String privateKey = "E523E8E83EEEDD55A775B0FEBAB422E5AFE02EC3C225F71DA259E0349428854D";
    	
    	// create transaction
    	RawTransaction rawTransaction  = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, address, data);
    	// sign transaction
    	byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, Credentials.create(privateKey));
    	// serialize transaction
    	String hexValue = Numeric.toHexString(signedMessage);
    	// send transaction
    	EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();

    	String txHash = ethSendTransaction.getTransactionHash();

    	return txHash;
    }
    
    public static String getTxDataByHash(String txHash) throws InterruptedException, ExecutionException
    {
    	Web3j web3 = Web3j.build(new HttpService("https://ropsten.infura.io/v3/d70de84d1eb74dfba5f4b3280f09bbcc"));
 
    	EthTransaction ethTx = web3.ethGetTransactionByHash(txHash).sendAsync().get();
    	Transaction tx = ethTx.getTransaction().get();
    	String data = Strings.fromUTF8ByteArray(Numeric.hexStringToByteArray(tx.getInput()));

    	return data;
    }

    public static void getTokenBalance(String account) throws Exception
    {
    	Web3j web3 = Web3j.build(new HttpService("https://ropsten.infura.io/v3/d70de84d1eb74dfba5f4b3280f09bbcc"));
    	
    	String contractAddress = "0x62A25206ac19E2467871F8C4b2566c464D98041d";
    	BigInteger gasPrice = DefaultGasProvider.GAS_PRICE;
    	BigInteger gasLimit = DefaultGasProvider.GAS_LIMIT;
    	TransactionManager transactionManager = new ClientTransactionManager(web3, account);
    	StandardToken contract = StandardToken.load(contractAddress, web3, transactionManager, gasPrice, gasLimit);
    	BigInteger balance =  contract.balanceOf(account).send();
    	System.out.printf("balance: %s\n", balance);
    }
    
    public static void sendTokens(String toAddress, String amount) throws Exception
    {
    	Web3j web3 = Web3j.build(new HttpService("https://ropsten.infura.io/v3/d70de84d1eb74dfba5f4b3280f09bbcc"));
    	
    	String fromAddress = "0xa5433a9c6f65ffefd1f8edc7970216edcbce5ac3";
    	String contractAddress = "0x62A25206ac19E2467871F8C4b2566c464D98041d";
    	String privateKey = "E523E8E83EEEDD55A775B0FEBAB422E5AFE02EC3C225F71DA259E0349428854D";  // corresponding to from address
    	BigInteger gasPrice = DefaultGasProvider.GAS_PRICE;
    	BigInteger gasLimit = DefaultGasProvider.GAS_LIMIT;
    	TransactionManager transactionManager = new ClientTransactionManager(web3, fromAddress);
    	StandardToken contract = StandardToken.load(contractAddress, web3, transactionManager, gasPrice, gasLimit);

    	// build function
    	String methodName = "transfer";
    	List<Type> inputParameters = new ArrayList<>();
    	List<TypeReference<?>> outputParameters = new ArrayList<>();
    	Address address = new Address(toAddress);
    	BigInteger decimals = contract.decimals().send();
    	BigDecimal amountValue = new BigDecimal(amount);
    	Uint256 value = new Uint256(amountValue.multiply(BigDecimal.TEN.pow(decimals.intValue())).toBigInteger());
    	inputParameters.add(address);
    	inputParameters.add(value);
    	TypeReference<Bool> typeReference = new TypeReference<Bool>() {};
    	outputParameters.add(typeReference);
    	Function function = new Function(methodName, inputParameters, outputParameters);

    	// build transaction
    	String data = FunctionEncoder.encode(function);
    	EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(fromAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
    	BigInteger nonce = ethGetTransactionCount.getTransactionCount();
    	RawTransaction rawTransaction  = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddress, data);
    	byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, Credentials.create(privateKey));
    	String hexValue = Numeric.toHexString(signedMessage);
    	
    	// send transaction
    	EthSendTransaction tx = web3.ethSendRawTransaction(hexValue).sendAsync().get();
    	System.out.printf("transaction: %s\n", tx.getTransactionHash());
    }

    public static void callContract() throws Exception
    {
    	Web3j web3 = Web3j.build(new HttpService("https://ropsten.infura.io/v3/d70de84d1eb74dfba5f4b3280f09bbcc"));
    	
    	String accountAddress = "0xa5433a9c6f65ffefd1f8edc7970216edcbce5ac3";
    	String contractAddress = "0x62A25206ac19E2467871F8C4b2566c464D98041d";
    	BigInteger gasPrice = DefaultGasProvider.GAS_PRICE;
    	BigInteger gasLimit = DefaultGasProvider.GAS_LIMIT;
    	TransactionManager transactionManager = new ClientTransactionManager(web3, accountAddress);
    	StandardToken contract = StandardToken.load(contractAddress, web3, transactionManager, gasPrice, gasLimit);
 
    	// get total supply
    	BigInteger totalSupply = contract.totalSupply().send();
    	System.out.printf("totalSupply: %s\n", totalSupply);
    	
    	// get balance
    	BigInteger balance =  contract.balanceOf(accountAddress).send();
    	System.out.printf("balance: %s\n", balance);
    	
    	// get decimal
    	BigInteger decimals = contract.decimals().send();
    	System.out.printf("decimals: %s\n", decimals);
    	
    	// transfer value
    	/////////////////////////////////////BEGIN//////////////////////////////////////////
    	// method1, not supported by infura.io
//    	String toAddress = "0xfa10329a8b2326476e93eb60cab21c69fe4130d6";
//    	TransactionReceipt tx = contract.transfer(toAddress, BigInteger.valueOf(1000L)).send();
//    	System.out.printf("transfer transaction: %s\n", tx.getTransactionHash());
    	
    	// method2, need signature
    	String methodName = "transfer";
    	List<Type> inputParameters = new ArrayList<>();
    	List<TypeReference<?>> outputParameters = new ArrayList<>();
    	Address toAddress = new Address("0xfa10329a8b2326476e93eb60cab21c69fe4130d6");
    	Uint256 value = new Uint256(BigDecimal.valueOf(1000).multiply(BigDecimal.TEN.pow(decimals.intValue())).toBigInteger());
    	inputParameters.add(toAddress);
    	inputParameters.add(value);
    	TypeReference<Bool> typeReference = new TypeReference<Bool>() {};
    	outputParameters.add(typeReference);
    	Function function = new Function(methodName, inputParameters, outputParameters);
    	String data = FunctionEncoder.encode(function);
 
    	EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(accountAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
    	BigInteger nonce = ethGetTransactionCount.getTransactionCount();
    	RawTransaction rawTransaction  = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddress, data);
    	String privateKey = "E523E8E83EEEDD55A775B0FEBAB422E5AFE02EC3C225F71DA259E0349428854D";
    	byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, Credentials.create(privateKey));
    	String hexValue = Numeric.toHexString(signedMessage);
    	EthSendTransaction tx = web3.ethSendRawTransaction(hexValue).sendAsync().get();
    	System.out.printf("transaction: %s\n", tx.getTransactionHash());
    	EthGetTransactionReceipt txReceipt;
		System.out.println("waiting for confirmation ...");
    	while (true)
    	{
    		txReceipt = web3.ethGetTransactionReceipt(tx.getTransactionHash()).sendAsync().get();
    		if (!txReceipt.getTransactionReceipt().isPresent())
    		{
    			Thread.sleep(1000);
    			continue;
    		}
			System.out.printf("transaction confirmed\n");
			TransactionReceipt transactionReceipt = txReceipt.getTransactionReceipt().get();
			System.out.printf("status: %s\n", transactionReceipt.getStatus().equals("0x1") ? "success" : "failure");
			System.out.printf("blocknumber: %d\n", transactionReceipt.getBlockNumber());
			System.out.printf("gasUsed: %d\n", transactionReceipt.getGasUsed());
			//System.out.println(transactionReceipt.getLogs());
			List<TransferEventResponse> eventResponse = contract.getTransferEvents(transactionReceipt);
			TransferEventResponse event = eventResponse.get(0);
			//System.out.println(event.log);
			System.out.printf("from: %s\n", event._from);
			System.out.printf("to: %s\n", event._to);
			System.out.printf("value: %d\n", event._value);
			break;
    	}
    	/////////////////////////////////////END//////////////////////////////////////////
    }
}
