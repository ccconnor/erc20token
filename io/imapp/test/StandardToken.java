package io.imapp.test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.5.0.
 */
public class StandardToken extends Contract {
    private static final String BINARY = "60806040523480156200001157600080fd5b5060405162001322380380620013228339810160409081528151602080840151928401516060850151600384905593850180519395909491019290916200005e91600091860190620000bb565b50815162000074906001906020850190620000bb565b506002805460ff191660ff9290921691909117905550506003543360008181526005602052604090209190915560048054600160a060020a03191690911790555062000160565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10620000fe57805160ff19168380011785556200012e565b828001600101855582156200012e579182015b828111156200012e57825182559160200191906001019062000111565b506200013c92915062000140565b5090565b6200015d91905b808211156200013c576000815560010162000147565b90565b6111b280620001706000396000f3006080604052600436106100f05763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166306fdde0381146100f5578063095ea7b31461017f57806318160ddd146101b757806323b872dd146101de578063313ce567146102085780633893966d1461023357806342966c68146102c657806370a08231146102de57806379c65068146102ff57806379cc6790146103255780638da5cb5b1461034957806395d89b411461037a578063a9059cbb1461038f578063b414d4b6146103b3578063cae9ca51146103d4578063dd62ed3e1461043d578063e724529c14610464575b600080fd5b34801561010157600080fd5b5061010a61048a565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561014457818101518382015260200161012c565b50505050905090810190601f1680156101715780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561018b57600080fd5b506101a3600160a060020a0360043516602435610518565b604080519115158252519081900360200190f35b3480156101c357600080fd5b506101cc61057e565b60408051918252519081900360200190f35b3480156101ea57600080fd5b506101a3600160a060020a0360043581169060243516604435610584565b34801561021457600080fd5b5061021d610778565b6040805160ff9092168252519081900360200190f35b34801561023f57600080fd5b50604080516020600480358082013583810280860185019096528085526101a395369593946024949385019291829185019084908082843750506040805187358901803560208181028481018201909552818452989b9a9989019892975090820195509350839250850190849080828437509497505050923560ff16935061078192505050565b3480156102d257600080fd5b506101a3600435610a72565b3480156102ea57600080fd5b506101cc600160a060020a0360043516610af6565b34801561030b57600080fd5b50610323600160a060020a0360043516602435610b11565b005b34801561033157600080fd5b506101a3600160a060020a0360043516602435610c38565b34801561035557600080fd5b5061035e610d20565b60408051600160a060020a039092168252519081900360200190f35b34801561038657600080fd5b5061010a610d2f565b34801561039b57600080fd5b506101a3600160a060020a0360043516602435610d89565b3480156103bf57600080fd5b506101a3600160a060020a0360043516610f17565b3480156103e057600080fd5b50604080516020600460443581810135601f81018490048402850184019095528484526101a3948235600160a060020a0316946024803595369594606494920191908190840183828082843750949750610f2c9650505050505050565b34801561044957600080fd5b506101cc600160a060020a0360043581169060243516611045565b34801561047057600080fd5b50610323600160a060020a03600435166024351515611070565b6000805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156105105780601f106104e557610100808354040283529160200191610510565b820191906000526020600020905b8154815290600101906020018083116104f357829003601f168201915b505050505081565b336000818152600660209081526040808320600160a060020a038716808552908352818420869055815186815291519394909390927f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925928290030190a350600192915050565b60035481565b6000600160a060020a038316151561059b57600080fd5b600160a060020a03841660009081526007602052604090205460ff161561060c576040805160e560020a62461bcd02815260206004820152601560248201527f736f75726365206163636f756e742066726f7a656e0000000000000000000000604482015290519081900360640190fd5b600160a060020a03831660009081526007602052604090205460ff161561067d576040805160e560020a62461bcd02815260206004820152601a60248201527f64657374696e6174696f6e206163636f756e742066726f7a656e000000000000604482015290519081900360640190fd5b600160a060020a0384166000908152600560205260409020546106a6908363ffffffff61113616565b600160a060020a0380861660009081526005602052604080822093909355908516815220546106db908363ffffffff61114d16565b600160a060020a03808516600090815260056020908152604080832094909455918716815260068252828120338252909152205461071f908363ffffffff61113616565b600160a060020a0380861660008181526006602090815260408083203384528252918290209490945580518681529051928716939192600080516020611167833981519152929181900390910190a35060019392505050565b60025460ff1681565b336000908152600760205260408120548190819060ff16156107ed576040805160e560020a62461bcd02815260206004820152601560248201527f736f75726365206163636f756e742066726f7a656e0000000000000000000000604482015290519081900360640190fd5b5060009050805b8360ff168210156108ed57858281518110151561080d57fe5b60209081029091010151600160a060020a0316151561082b57600080fd5b60076000878481518110151561083d57fe5b6020908102909101810151600160a060020a031682528101919091526040016000205460ff16156108b8576040805160e560020a62461bcd02815260206004820152601a60248201527f64657374696e6174696f6e206163636f756e742066726f7a656e000000000000604482015290519081900360640190fd5b6108e085838151811015156108c957fe5b60209081029091010151829063ffffffff61114d16565b60019092019190506107f4565b3360009081526005602052604090205481111561090957600080fd5b600091505b8360ff16821015610a6657610952858381518110151561092a57fe5b602090810290910181015133600090815260059092526040909120549063ffffffff61113616565b3360009081526005602052604090205584516109bf9086908490811061097457fe5b9060200190602002015160056000898681518110151561099057fe5b6020908102909101810151600160a060020a03168252810191909152604001600020549063ffffffff61114d16565b6005600088858151811015156109d157fe5b6020908102909101810151600160a060020a03168252810191909152604001600020558551869083908110610a0257fe5b90602001906020020151600160a060020a031633600160a060020a03166000805160206111678339815191528785815181101515610a3c57fe5b906020019060200201516040518082815260200191505060405180910390a360019091019061090e565b50600195945050505050565b33600090815260056020526040812054610a92908363ffffffff61113616565b33600090815260056020526040902055600354610ab5908363ffffffff61113616565b60035560408051838152905133917fcc16f5dbb4873280815c1ee09dbd06736cffcc184412cf7a71a0fdb75d397ca5919081900360200190a2506001919050565b600160a060020a031660009081526005602052604090205490565b600454600160a060020a03163314610b73576040805160e560020a62461bcd02815260206004820152601160248201527f7065726d697373696f6e2064656e696564000000000000000000000000000000604482015290519081900360640190fd5b600160a060020a0382161515610b8857600080fd5b600160a060020a038216600090815260056020526040902054610bb1908263ffffffff61114d16565b600160a060020a038316600090815260056020526040902055600354610bdd908263ffffffff61114d16565b60035560408051828152905133916000916000805160206111678339815191529181900360200190a3604080518281529051600160a060020a0384169133916000805160206111678339815191529181900360200190a35050565b600160a060020a038216600090815260056020526040812054610c61908363ffffffff61113616565b600160a060020a0384166000908152600560209081526040808320939093556006815282822033835290522054610c9e908363ffffffff61113616565b600160a060020a0384166000908152600660209081526040808320338452909152902055600354610cd5908363ffffffff61113616565b600355604080518381529051600160a060020a038516917fcc16f5dbb4873280815c1ee09dbd06736cffcc184412cf7a71a0fdb75d397ca5919081900360200190a250600192915050565b600454600160a060020a031681565b60018054604080516020600284861615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156105105780601f106104e557610100808354040283529160200191610510565b6000600160a060020a0383161515610da057600080fd5b3360009081526007602052604090205460ff1615610e08576040805160e560020a62461bcd02815260206004820152601560248201527f736f75726365206163636f756e742066726f7a656e0000000000000000000000604482015290519081900360640190fd5b600160a060020a03831660009081526007602052604090205460ff1615610e79576040805160e560020a62461bcd02815260206004820152601a60248201527f64657374696e6174696f6e206163636f756e742066726f7a656e000000000000604482015290519081900360640190fd5b33600090815260056020526040902054610e99908363ffffffff61113616565b3360009081526005602052604080822092909255600160a060020a03851681522054610ecb908363ffffffff61114d16565b600160a060020a0384166000818152600560209081526040918290209390935580518581529051919233926000805160206111678339815191529281900390910190a350600192915050565b60076020526000908152604090205460ff1681565b600083610f398185610518565b1561103d576040517f8f4ffcb10000000000000000000000000000000000000000000000000000000081523360048201818152602483018790523060448401819052608060648501908152875160848601528751600160a060020a03871695638f4ffcb195948b94938b939192909160a490910190602085019080838360005b83811015610fd1578181015183820152602001610fb9565b50505050905090810190601f168015610ffe5780820380516001836020036101000a031916815260200191505b5095505050505050600060405180830381600087803b15801561102057600080fd5b505af1158015611034573d6000803e3d6000fd5b50505050600191505b509392505050565b600160a060020a03918216600090815260066020908152604080832093909416825291909152205490565b600454600160a060020a031633146110d2576040805160e560020a62461bcd02815260206004820152601160248201527f7065726d697373696f6e2064656e696564000000000000000000000000000000604482015290519081900360640190fd5b600160a060020a038216600081815260076020908152604091829020805460ff191685151590811790915582519384529083015280517f48335238b4855f35377ed80f164e8c6f3c366e54ac00b96a6402d4a9814a03a59281900390910190a15050565b6000808383111561114657600080fd5b5050900390565b60008282018381101561115f57600080fd5b93925050505600ddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3efa165627a7a72305820f903ad9591aba5b9e08fb85e5ac7cc216fa95378c3dfa5602b828745419115690029";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final String FUNC_DECIMALS = "decimals";

    public static final String FUNC_DISTRIBUTE = "distribute";

    public static final String FUNC_BURN = "burn";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_MINTTOKEN = "mintToken";

    public static final String FUNC_BURNFROM = "burnFrom";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_FROZENACCOUNT = "frozenAccount";

    public static final String FUNC_APPROVEANDCALL = "approveAndCall";

    public static final String FUNC_ALLOWANCE = "allowance";

    public static final String FUNC_FREEZEACCOUNT = "freezeAccount";

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event APPROVAL_EVENT = new Event("Approval", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event BURN_EVENT = new Event("Burn", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event FROZENFUNDS_EVENT = new Event("FrozenFunds", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
    ;

    protected StandardToken(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected StandardToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<String> name() {
        final Function function = new Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> approve(String _spender, BigInteger _value) {
        final Function function = new Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_spender), 
                new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> totalSupply() {
        final Function function = new Function(FUNC_TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> transferFrom(String _from, String _to, BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_from), 
                new org.web3j.abi.datatypes.Address(_to), 
                new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> decimals() {
        final Function function = new Function(FUNC_DECIMALS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> distribute(List<String> _toAddrs, List<BigInteger> _values, BigInteger count) {
        final Function function = new Function(
                FUNC_DISTRIBUTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.Utils.typeMap(_toAddrs, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.Utils.typeMap(_values, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.generated.Uint8(count)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> burn(BigInteger _value) {
        final Function function = new Function(
                FUNC_BURN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> balanceOf(String _owner) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> mintToken(String target, BigInteger mintedAmount) {
        final Function function = new Function(
                FUNC_MINTTOKEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(target), 
                new org.web3j.abi.datatypes.generated.Uint256(mintedAmount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> burnFrom(String _from, BigInteger _value) {
        final Function function = new Function(
                FUNC_BURNFROM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_from), 
                new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> symbol() {
        final Function function = new Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> transfer(String _to, BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_to), 
                new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> frozenAccount(String param0) {
        final Function function = new Function(FUNC_FROZENACCOUNT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> approveAndCall(String _spender, BigInteger _value, byte[] _extraData) {
        final Function function = new Function(
                FUNC_APPROVEANDCALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_spender), 
                new org.web3j.abi.datatypes.generated.Uint256(_value), 
                new org.web3j.abi.datatypes.DynamicBytes(_extraData)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> allowance(String _owner, String _spender) {
        final Function function = new Function(FUNC_ALLOWANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner), 
                new org.web3j.abi.datatypes.Address(_spender)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> freezeAccount(String target, Boolean freeze) {
        final Function function = new Function(
                FUNC_FREEZEACCOUNT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(target), 
                new org.web3j.abi.datatypes.Bool(freeze)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<StandardToken> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialSupply, String tokenName, String tokenSymbol, BigInteger tokenDecimals) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(initialSupply), 
                new org.web3j.abi.datatypes.Utf8String(tokenName), 
                new org.web3j.abi.datatypes.Utf8String(tokenSymbol), 
                new org.web3j.abi.datatypes.generated.Uint8(tokenDecimals)));
        return deployRemoteCall(StandardToken.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<StandardToken> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialSupply, String tokenName, String tokenSymbol, BigInteger tokenDecimals) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(initialSupply), 
                new org.web3j.abi.datatypes.Utf8String(tokenName), 
                new org.web3j.abi.datatypes.Utf8String(tokenSymbol), 
                new org.web3j.abi.datatypes.generated.Uint8(tokenDecimals)));
        return deployRemoteCall(StandardToken.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TransferEventResponse> transferEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<TransferEventResponse> transferEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventObservable(filter);
    }

    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._spender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ApprovalEventResponse> approvalEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ApprovalEventResponse>() {
            @Override
            public ApprovalEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVAL_EVENT, log);
                ApprovalEventResponse typedResponse = new ApprovalEventResponse();
                typedResponse.log = log;
                typedResponse._owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._spender = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ApprovalEventResponse> approvalEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventObservable(filter);
    }

    public List<BurnEventResponse> getBurnEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BURN_EVENT, transactionReceipt);
        ArrayList<BurnEventResponse> responses = new ArrayList<BurnEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BurnEventResponse typedResponse = new BurnEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<BurnEventResponse> burnEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, BurnEventResponse>() {
            @Override
            public BurnEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BURN_EVENT, log);
                BurnEventResponse typedResponse = new BurnEventResponse();
                typedResponse.log = log;
                typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<BurnEventResponse> burnEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BURN_EVENT));
        return burnEventObservable(filter);
    }

    public List<FrozenFundsEventResponse> getFrozenFundsEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(FROZENFUNDS_EVENT, transactionReceipt);
        ArrayList<FrozenFundsEventResponse> responses = new ArrayList<FrozenFundsEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FrozenFundsEventResponse typedResponse = new FrozenFundsEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.target = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.frozen = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<FrozenFundsEventResponse> frozenFundsEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, FrozenFundsEventResponse>() {
            @Override
            public FrozenFundsEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(FROZENFUNDS_EVENT, log);
                FrozenFundsEventResponse typedResponse = new FrozenFundsEventResponse();
                typedResponse.log = log;
                typedResponse.target = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.frozen = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<FrozenFundsEventResponse> frozenFundsEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FROZENFUNDS_EVENT));
        return frozenFundsEventObservable(filter);
    }

    public static StandardToken load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new StandardToken(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static StandardToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new StandardToken(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class TransferEventResponse {
        public Log log;

        public String _from;

        public String _to;

        public BigInteger _value;
    }

    public static class ApprovalEventResponse {
        public Log log;

        public String _owner;

        public String _spender;

        public BigInteger _value;
    }

    public static class BurnEventResponse {
        public Log log;

        public String _from;

        public BigInteger _value;
    }

    public static class FrozenFundsEventResponse {
        public Log log;

        public String target;

        public Boolean frozen;
    }
}
