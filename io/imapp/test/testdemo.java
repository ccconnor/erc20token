package io.imapp.test;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;


public class testdemo {
    public static void main( String[] args ) throws Exception {
    	Web3j web3 = Web3j.build(new HttpService("https://ropsten.infura.io/v3/d70de84d1eb74dfba5f4b3280f09bbcc"));
    	String contractAddress = "0x62A25206ac19E2467871F8C4b2566c464D98041d";
    	String accountAddress = "0xa5433a9c6f65ffefd1f8edc7970216edcbce5ac3";
    	String toAddress = "0xfa10329a8b2326476e93eb60cab21c69fe4130d6";
    	String privateKey = "E523E8E83EEEDD55A775B0FEBAB422E5AFE02EC3C225F71DA259E0349428854D";
    	MyToken token = new MyToken(web3, contractAddress, accountAddress, privateKey);

    	// get test
    	System.out.printf("name: %s\n", token.name());
    	System.out.printf("symbol: %s\n", token.symbol());
    	System.out.printf("decimals: %s\n", token.decimals().toString());
    	System.out.printf("totalSupply: %s\n", token.totalSupply().toString());
    	System.out.printf("owner: %s\n", token.owner());
    	System.out.printf("balance of [%s]: %s\n", accountAddress, token.balanceOf(accountAddress));
    	System.out.printf("[%s] frozen? %s\n", accountAddress, token.frozenAccount(accountAddress) ? "true" : "false");
    	System.out.printf("[%s] allow [%s] spend %s tokens\n", toAddress, accountAddress, token.allowance(toAddress, accountAddress));

    	// transfer test
    	String txHash;
    	txHash = token.transfer(toAddress, BigInteger.valueOf(1000L));
    	System.out.println(txHash);

    	// mint test
    	txHash = token.mint(accountAddress, BigInteger.valueOf(1000L));
    	System.out.println(txHash);
    	
    	// distribute test
    	Map<String, BigInteger> valueList = new HashMap<String, BigInteger>();
    	valueList.put("0xfa10329a8b2326476e93eb60cab21c69fe4130d6", BigInteger.valueOf(1000L));
    	txHash = token.distribute(valueList);
    	System.out.println(txHash);
    }
}