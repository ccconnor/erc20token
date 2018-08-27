package io.imapp.test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.Utils;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;

public class MyToken {
	static public final BigInteger gasPrice = DefaultGasProvider.GAS_PRICE;
	static public final BigInteger gasLimit = DefaultGasProvider.GAS_LIMIT;

	private Web3j web3;
	private String contractAddress;
	private String accountAddress;
	private String privateKey;
	private StandardToken contract;
	private BigInteger nonce;

	protected MyToken(Web3j web3, String contractAddress, String accountAddress, String privateKey) {
		this.web3 = web3;
		this.contractAddress = contractAddress;
		this.accountAddress = accountAddress;
		this.privateKey = privateKey;
		TransactionManager transactionManager = new ClientTransactionManager(web3, accountAddress);
		this.contract = StandardToken.load(contractAddress, web3, transactionManager, DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT);
		this.nonce = BigInteger.ZERO;
	}

	public String name() {
		try {
			return contract.name().send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public BigInteger totalSupply() {
		try {
			return contract.totalSupply().send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return BigInteger.ZERO;
		}
	}
	
	public BigInteger decimals() {
		try {
			return contract.decimals().send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return BigInteger.ZERO;
		}
	}
	
	public BigInteger balanceOf(String address) {
		try {
			return contract.balanceOf(address).send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return BigInteger.ZERO;
		}
	}
	
	public String owner() {
		try {
			return contract.owner().send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public String symbol() {
		try {
			return contract.symbol().send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Boolean frozenAccount(String address) {
		try {
			return contract.frozenAccount(address).send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public BigInteger allowance(String owner, String spender) {
		try {
			return contract.allowance(owner, spender).send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return BigInteger.ZERO;
		}
	}

	private BigInteger getTransactionCount() throws InterruptedException, ExecutionException {
		EthGetTransactionCount ethGetTransactionCount;
		ethGetTransactionCount = web3.ethGetTransactionCount(accountAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
		return ethGetTransactionCount.getTransactionCount();
	}

	private String sendTransaction(Function function) {
    	// build transaction
    	String data = FunctionEncoder.encode(function);
    	if (nonce.equals(BigInteger.ZERO)) {
	    	try {
	    		nonce = getTransactionCount();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return null;
			} catch (ExecutionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return null;
			}
    	} else {
    		nonce = nonce.add(BigInteger.ONE);
    	}

    	RawTransaction rawTransaction  = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddress, data);
    	byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, Credentials.create(privateKey));
    	String hexValue = Numeric.toHexString(signedMessage);
    	
    	// send transaction
    	EthSendTransaction tx;
		try {
			tx = web3.ethSendRawTransaction(hexValue).sendAsync().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

    	return tx.getTransactionHash();
	}

	public String approve(String spender, BigInteger value) {
		// build function
    	String methodName = "approve";
    	List<Type> inputParameters = new ArrayList<>();
    	List<TypeReference<?>> outputParameters = new ArrayList<>();
    	Address spenderAddress = new Address(spender);
    	Uint256 tokenValue = new Uint256(value);
    	inputParameters.add(spenderAddress);
    	inputParameters.add(tokenValue);
    	TypeReference<Bool> typeReference = new TypeReference<Bool>() {};
    	outputParameters.add(typeReference);
    	Function function = new Function(methodName, inputParameters, outputParameters);

    	return sendTransaction(function);
	}

	public String transfer(String toAddress, BigInteger value) {
    	// build function
    	String methodName = "transfer";
    	List<Type> inputParameters = new ArrayList<>();
    	List<TypeReference<?>> outputParameters = new ArrayList<>();
    	Address address = new Address(toAddress);
    	Uint256 amount = new Uint256(value);
    	inputParameters.add(address);
    	inputParameters.add(amount);
    	TypeReference<Bool> typeReference = new TypeReference<Bool>() {};
    	outputParameters.add(typeReference);
    	Function function = new Function(methodName, inputParameters, outputParameters);

    	return sendTransaction(function);
	}

	public String transferFrom(String from, String to, BigInteger value) {
    	// build function
        final Function function = new Function(
                "transferFrom", 
                Arrays.<Type>asList(new Address(from), 
                new Address(to), 
                new Uint256(value)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));

    	return sendTransaction(function);
	}

	public String mint(String toAddress, BigInteger amount) {
    	// build function
    	String methodName = "mintToken";
    	List<Type> inputParameters = new ArrayList<>();
    	List<TypeReference<?>> outputParameters = new ArrayList<>();
    	Address address = new Address(toAddress);
    	Uint256 value = new Uint256(amount);
    	inputParameters.add(address);
    	inputParameters.add(value);
    	Function function = new Function(methodName, inputParameters, outputParameters);
    	
    	return sendTransaction(function);
	}
	
	public String burn(BigInteger amount) {
    	// build function
    	String methodName = "burn";
    	List<Type> inputParameters = new ArrayList<>();
    	List<TypeReference<?>> outputParameters = new ArrayList<>();
    	Uint256 value = new Uint256(amount);
    	inputParameters.add(value);
    	TypeReference<Bool> typeReference = new TypeReference<Bool>() {};
    	outputParameters.add(typeReference);
    	Function function = new Function(methodName, inputParameters, outputParameters);

    	return sendTransaction(function);
	}
	
	public String freezeAccount(String account) {
    	// build function
    	String methodName = "freezeAccount";
    	List<Type> inputParameters = new ArrayList<>();
    	List<TypeReference<?>> outputParameters = new ArrayList<>();
    	Address address = new Address(account);
    	Bool freeze = new Bool(true);
    	inputParameters.add(address);
    	inputParameters.add(freeze);
    	TypeReference<Bool> typeReference = new TypeReference<Bool>() {};
    	outputParameters.add(typeReference);
    	Function function = new Function(methodName, inputParameters, outputParameters);

    	return sendTransaction(function);
	}

	public String unfreezeAccount(String account) {
    	// build function
    	String methodName = "freezeAccount";
    	List<Type> inputParameters = new ArrayList<>();
    	List<TypeReference<?>> outputParameters = new ArrayList<>();
    	Address address = new Address(account);
    	Bool freeze = new Bool(false);
    	inputParameters.add(address);
    	inputParameters.add(freeze);
    	TypeReference<Bool> typeReference = new TypeReference<Bool>() {};
    	outputParameters.add(typeReference);
    	Function function = new Function(methodName, inputParameters, outputParameters);

    	return sendTransaction(function);
	}

	public String distribute(Map<String, BigInteger> valueList) {
		Uint8 count = new Uint8(valueList.size());
		if (count.equals(new Uint8(0))) {
			return null;
		}

		DynamicArray<Address> addressList = new DynamicArray<Address>(Utils.typeMap(new ArrayList(valueList.keySet()), Address.class));
		DynamicArray<Uint256> amountList = new DynamicArray<Uint256>(Utils.typeMap(new ArrayList(valueList.values()), Uint256.class));
    	String methodName = "distribute";
    	List<Type> inputParameters = new ArrayList<>();
    	List<TypeReference<?>> outputParameters = new ArrayList<>();
    	inputParameters.add(addressList);
    	inputParameters.add(amountList);
    	inputParameters.add(count);
    	TypeReference<Bool> typeReference = new TypeReference<Bool>() {};
    	outputParameters.add(typeReference);
    	Function function = new Function(methodName, inputParameters, outputParameters);
    	
    	return sendTransaction(function);
	}
}