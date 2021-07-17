package org.web3j;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple9;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.16.
 */
@SuppressWarnings("rawtypes")
public class NonRepudiationOfXu extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051611542380380611542833981810160405260c081101561003357600080fd5b81019080805190602001909291908051906020019092919080519060200190929190805190602001909291908051906020019092919080519060200190929190505050856000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555084600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506000600581905550836006819055506000600781905550600060028190555082600a8190555081600b8190555080600c81905550505050505050611402806101406000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c806393fa4c161161005b57806393fa4c161461019457806399ee27b51461019e5780639f410738146101ef578063e44d26ac146102aa57610088565b8063024c9e3b1461008d5780632cbdc10014610097578063472b370d14610152578063514289eb1461015c575b600080fd5b6100956102e2565b005b610150600480360360208110156100ad57600080fd5b81019080803590602001906401000000008111156100ca57600080fd5b8201836020820111156100dc57600080fd5b803590602001918460018302840111640100000000831117156100fe57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050919291929050505061078f565b005b61015a61091a565b005b6101926004803603604081101561017257600080fd5b810190808035906020019092919080359060200190929190505050610b5e565b005b61019c610d02565b005b6101a6610f14565b604051808373ffffffffffffffffffffffffffffffffffffffff1681526020018273ffffffffffffffffffffffffffffffffffffffff1681526020019250505060405180910390f35b6101f7610f63565b60405180806020018a815260200189815260200188815260200187815260200186815260200185815260200184815260200183815260200182810382528b818151815260200191508051906020019080838360005b8381101561026757808201518184015260208101905061024c565b50505050905090810190601f1680156102945780820380516001836020036101000a031916815260200191505b509a505050505050505050505060405180910390f35b6102e0600480360360408110156102c057600080fd5b810190808035906020019092919080359060200190929190505050611041565b005b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614806103895750600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16145b6103fb576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601d8152602001807f53686f756c642062652074686520636f7272656374206164647265737300000081525060200191505060405180910390fd5b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614156105d15760028054146104c6576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260168152602001807f4572726f7220736572766963652073657175656e63650000000000000000000081525060200191505060405180910390fd5b600654600554014211610541576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601c8152602001807f54686520636f6e6669726d2074696d65206973206e6f74206f7665720000000081525060200191505060405180910390fd5b7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff6002819055507fbe71414413aef46f2b154f7d58e2d89c07fbc85711d6cd859dc67a6265cf3d1d6040518080602001828103825260178152602001807f436c6f7564205465726d696e617465205365727669636500000000000000000081525060200191505060405180910390a15b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141561078d5760036002541461069f576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260168152602001807f4572726f7220736572766963652073657175656e63650000000000000000000081525060200191505060405180910390fd5b6006546007540142116106fd576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260228152602001806113446022913960400191505060405180910390fd5b7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff6002819055507fbe71414413aef46f2b154f7d58e2d89c07fbc85711d6cd859dc67a6265cf3d1d6040518080602001828103825260188152602001807f436c69656e74205465726d696e6174652053657276696365000000000000000081525060200191505060405180910390a15b565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610835576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260248152602001806113206024913960400191505060405180910390fd5b600060025414610890576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260238152602001806113896023913960400191505060405180910390fd5b80600890805190602001906108a6929190611282565b5060016002819055507f4b6d27f6fb159cec561279ade024f7ceb0509e8fb98a40f38cd849de504ac3376040518080602001828103825260178152602001807f436c69656e74205265717565737473205365727669636500000000000000000081525060200191505060405180910390a150565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146109c0576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260248152602001806113206024913960400191505060405180910390fd5b600460025414610a38576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260168152602001807f4572726f7220736572766963652073657175656e63650000000000000000000081525060200191505060405180910390fd5b60006001905060006009549050600c548110610a5d57600c548181610a5957fe5b0690505b60005b81811015610a8757600b54600a54840281610a7757fe5b0692508080600101915050610a60565b50600354600b54836004540281610a9a57fe5b061415610af1577f8b90e5ec748407584a6acd2b543d5e057bcc41b36b06d20cfe0a07eb317d2edd6040518080602001828103825260218152602001806113ac6021913960400191505060405180910390a1610b5a565b7f8b90e5ec748407584a6acd2b543d5e057bcc41b36b06d20cfe0a07eb317d2edd60405180806020018281038252601d8152602001807f4f6e2d636861696e20526573756c743a204d616c6963696f757320535000000081525060200191505060405180910390a15b5050565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610c02576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260238152602001806113666023913960400191505060405180910390fd5b600160025414610c7a576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260168152602001807f4572726f7220736572766963652073657175656e63650000000000000000000081525060200191505060405180910390fd5b816003819055508060048190555060028081905550426005819055507f71a779111dd608222f0ee381004288552151c258cadd96876bd7a684ba5ff5df60405180806020018281038252600f8152602001807f436c6f756420646f20526f756e6431000000000000000000000000000000000081525060200191505060405180910390a15050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610da8576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260248152602001806113206024913960400191505060405180910390fd5b6002805414610e1f576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260168152602001807f4572726f7220736572766963652073657175656e63650000000000000000000081525060200191505060405180910390fd5b60065460055401421115610e9b576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260188152602001807f54686520636f6e6669726d2074696d65206973206f766572000000000000000081525060200191505060405180910390fd5b6003600281905550426007819055507f8b0736a3e4227aa6e5120402894f2c2cd2c6e983e96b8d22d4560a33a8cfe08260405180806020018281038252600f8152602001807f436c69656e7420636f6e6669726d73000000000000000000000000000000000081525060200191505060405180910390a1565b60008060008054906101000a900473ffffffffffffffffffffffffffffffffffffffff169150600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690509091565b606060008060008060008060008060088054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156110075780601f10610fdc57610100808354040283529160200191611007565b820191906000526020600020905b815481529060010190602001808311610fea57829003601f168201915b505050505098506003549750600454965060055495506007549450600954935060065492506002549150600d549050909192939495969798565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146110e5576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260238152602001806113666023913960400191505060405180910390fd5b60036002541461115d576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260168152602001807f4572726f7220736572766963652073657175656e63650000000000000000000081525060200191505060405180910390fd5b600654600754014211156111ff577fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff6002819055507ffbb37fef1e24d6f05773bdb0b2f48507b92149924d04bb48bfd1ca4fb23778ae60405180806020018281038252601e8152602001807f546865207075626c6973682074696d65206f66207332206973206f766572000081525060200191505060405180910390a161127e565b81600981905550600460028190555080600d819055507ffbb37fef1e24d6f05773bdb0b2f48507b92149924d04bb48bfd1ca4fb23778ae60405180806020018281038252600f8152602001807f436c6f756420646f20526f756e6432000000000000000000000000000000000081525060200191505060405180910390a15b5050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106112c357805160ff19168380011785556112f1565b828001600101855582156112f1579182015b828111156112f05782518255916020019190600101906112d5565b5b5090506112fe9190611302565b5090565b5b8082111561131b576000816000905550600101611303565b509056fe53686f756c642062652074686520636f727265637420636c69656e742061646472657373546865207075626c6973682074696d65206f66207332206973206e6f74206f76657253686f756c642062652074686520636f727265637420636c6f756420616464726573735365727669636520616c72656164792073746172746564206f722066696e69736865644f6e2d636861696e20526573756c743a204d616c6963696f757320436c69656e74a26469706673582212209c60f04cb72cf5cef369370dfd319dd01ab33ae7edefd5fe6c2fa74979c3fc0964736f6c634300060c0033";

    public static final String FUNC_ARBITRATE = "arbitrate";

    public static final String FUNC_CLIENTCONFIRM = "clientConfirm";

    public static final String FUNC_CLOUDDOROUND1 = "cloudDoRound1";

    public static final String FUNC_CLOUDDOROUND2 = "cloudDoRound2";

    public static final String FUNC_GETBASICINFOS = "getBasicInfos";

    public static final String FUNC_GETDETAILSINFOS = "getDetailsInfos";

    public static final String FUNC_REQUESTSERVICE = "requestService";

    public static final String FUNC_TERMINATEDSERVICE = "terminatedService";

    public static final Event ARBITRATE_EVENT = new Event("Arbitrate", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event CLIENTCONFIRM_EVENT = new Event("ClientConfirm", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event CLOUDDOROUND1_EVENT = new Event("CloudDoRound1", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event CLOUDDOROUND2_EVENT = new Event("CloudDoRound2", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event REQUESTSERVICE_EVENT = new Event("RequestService", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event TERMINATEDSERVICE_EVENT = new Event("TerminatedService", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected NonRepudiationOfXu(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected NonRepudiationOfXu(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected NonRepudiationOfXu(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected NonRepudiationOfXu(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<ArbitrateEventResponse> getArbitrateEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ARBITRATE_EVENT, transactionReceipt);
        ArrayList<ArbitrateEventResponse> responses = new ArrayList<ArbitrateEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ArbitrateEventResponse typedResponse = new ArbitrateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ret = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ArbitrateEventResponse> arbitrateEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ArbitrateEventResponse>() {
            @Override
            public ArbitrateEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ARBITRATE_EVENT, log);
                ArbitrateEventResponse typedResponse = new ArbitrateEventResponse();
                typedResponse.log = log;
                typedResponse.ret = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ArbitrateEventResponse> arbitrateEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ARBITRATE_EVENT));
        return arbitrateEventFlowable(filter);
    }

    public List<ClientConfirmEventResponse> getClientConfirmEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CLIENTCONFIRM_EVENT, transactionReceipt);
        ArrayList<ClientConfirmEventResponse> responses = new ArrayList<ClientConfirmEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ClientConfirmEventResponse typedResponse = new ClientConfirmEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ret = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ClientConfirmEventResponse> clientConfirmEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ClientConfirmEventResponse>() {
            @Override
            public ClientConfirmEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CLIENTCONFIRM_EVENT, log);
                ClientConfirmEventResponse typedResponse = new ClientConfirmEventResponse();
                typedResponse.log = log;
                typedResponse.ret = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ClientConfirmEventResponse> clientConfirmEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLIENTCONFIRM_EVENT));
        return clientConfirmEventFlowable(filter);
    }

    public List<CloudDoRound1EventResponse> getCloudDoRound1Events(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CLOUDDOROUND1_EVENT, transactionReceipt);
        ArrayList<CloudDoRound1EventResponse> responses = new ArrayList<CloudDoRound1EventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CloudDoRound1EventResponse typedResponse = new CloudDoRound1EventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ret = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CloudDoRound1EventResponse> cloudDoRound1EventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CloudDoRound1EventResponse>() {
            @Override
            public CloudDoRound1EventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CLOUDDOROUND1_EVENT, log);
                CloudDoRound1EventResponse typedResponse = new CloudDoRound1EventResponse();
                typedResponse.log = log;
                typedResponse.ret = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CloudDoRound1EventResponse> cloudDoRound1EventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLOUDDOROUND1_EVENT));
        return cloudDoRound1EventFlowable(filter);
    }

    public List<CloudDoRound2EventResponse> getCloudDoRound2Events(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CLOUDDOROUND2_EVENT, transactionReceipt);
        ArrayList<CloudDoRound2EventResponse> responses = new ArrayList<CloudDoRound2EventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CloudDoRound2EventResponse typedResponse = new CloudDoRound2EventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ret = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CloudDoRound2EventResponse> cloudDoRound2EventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CloudDoRound2EventResponse>() {
            @Override
            public CloudDoRound2EventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CLOUDDOROUND2_EVENT, log);
                CloudDoRound2EventResponse typedResponse = new CloudDoRound2EventResponse();
                typedResponse.log = log;
                typedResponse.ret = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CloudDoRound2EventResponse> cloudDoRound2EventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLOUDDOROUND2_EVENT));
        return cloudDoRound2EventFlowable(filter);
    }

    public List<RequestServiceEventResponse> getRequestServiceEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REQUESTSERVICE_EVENT, transactionReceipt);
        ArrayList<RequestServiceEventResponse> responses = new ArrayList<RequestServiceEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RequestServiceEventResponse typedResponse = new RequestServiceEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ret = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RequestServiceEventResponse> requestServiceEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RequestServiceEventResponse>() {
            @Override
            public RequestServiceEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(REQUESTSERVICE_EVENT, log);
                RequestServiceEventResponse typedResponse = new RequestServiceEventResponse();
                typedResponse.log = log;
                typedResponse.ret = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RequestServiceEventResponse> requestServiceEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REQUESTSERVICE_EVENT));
        return requestServiceEventFlowable(filter);
    }

    public List<TerminatedServiceEventResponse> getTerminatedServiceEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TERMINATEDSERVICE_EVENT, transactionReceipt);
        ArrayList<TerminatedServiceEventResponse> responses = new ArrayList<TerminatedServiceEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TerminatedServiceEventResponse typedResponse = new TerminatedServiceEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ret = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TerminatedServiceEventResponse> terminatedServiceEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TerminatedServiceEventResponse>() {
            @Override
            public TerminatedServiceEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TERMINATEDSERVICE_EVENT, log);
                TerminatedServiceEventResponse typedResponse = new TerminatedServiceEventResponse();
                typedResponse.log = log;
                typedResponse.ret = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TerminatedServiceEventResponse> terminatedServiceEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TERMINATEDSERVICE_EVENT));
        return terminatedServiceEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> arbitrate() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ARBITRATE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> clientConfirm() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLIENTCONFIRM, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> cloudDoRound1(BigInteger verifyResult, BigInteger hashOfS1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLOUDDOROUND1, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(verifyResult), 
                new org.web3j.abi.datatypes.generated.Uint256(hashOfS1)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> cloudDoRound2(BigInteger s2, BigInteger randomBlock) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLOUDDOROUND2, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(s2), 
                new org.web3j.abi.datatypes.generated.Uint256(randomBlock)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple2<String, String>> getBasicInfos() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETBASICINFOS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        return new RemoteFunctionCall<Tuple2<String, String>>(function,
                new Callable<Tuple2<String, String>>() {
                    @Override
                    public Tuple2<String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple9<String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>> getDetailsInfos() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETDETAILSINFOS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Int256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple9<String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple9<String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple9<String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple9<String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (BigInteger) results.get(7).getValue(), 
                                (BigInteger) results.get(8).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> requestService(String serviceName) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REQUESTSERVICE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(serviceName)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> terminatedService() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TERMINATEDSERVICE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static NonRepudiationOfXu load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new NonRepudiationOfXu(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static NonRepudiationOfXu load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new NonRepudiationOfXu(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static NonRepudiationOfXu load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new NonRepudiationOfXu(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static NonRepudiationOfXu load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new NonRepudiationOfXu(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<NonRepudiationOfXu> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String cloud, String client, BigInteger duringTime, BigInteger g, BigInteger p, BigInteger q) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, cloud), 
                new org.web3j.abi.datatypes.Address(160, client), 
                new org.web3j.abi.datatypes.generated.Uint256(duringTime), 
                new org.web3j.abi.datatypes.generated.Uint256(g), 
                new org.web3j.abi.datatypes.generated.Uint256(p), 
                new org.web3j.abi.datatypes.generated.Uint256(q)));
        return deployRemoteCall(NonRepudiationOfXu.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<NonRepudiationOfXu> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String cloud, String client, BigInteger duringTime, BigInteger g, BigInteger p, BigInteger q) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, cloud), 
                new org.web3j.abi.datatypes.Address(160, client), 
                new org.web3j.abi.datatypes.generated.Uint256(duringTime), 
                new org.web3j.abi.datatypes.generated.Uint256(g), 
                new org.web3j.abi.datatypes.generated.Uint256(p), 
                new org.web3j.abi.datatypes.generated.Uint256(q)));
        return deployRemoteCall(NonRepudiationOfXu.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<NonRepudiationOfXu> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String cloud, String client, BigInteger duringTime, BigInteger g, BigInteger p, BigInteger q) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, cloud), 
                new org.web3j.abi.datatypes.Address(160, client), 
                new org.web3j.abi.datatypes.generated.Uint256(duringTime), 
                new org.web3j.abi.datatypes.generated.Uint256(g), 
                new org.web3j.abi.datatypes.generated.Uint256(p), 
                new org.web3j.abi.datatypes.generated.Uint256(q)));
        return deployRemoteCall(NonRepudiationOfXu.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<NonRepudiationOfXu> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String cloud, String client, BigInteger duringTime, BigInteger g, BigInteger p, BigInteger q) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, cloud), 
                new org.web3j.abi.datatypes.Address(160, client), 
                new org.web3j.abi.datatypes.generated.Uint256(duringTime), 
                new org.web3j.abi.datatypes.generated.Uint256(g), 
                new org.web3j.abi.datatypes.generated.Uint256(p), 
                new org.web3j.abi.datatypes.generated.Uint256(q)));
        return deployRemoteCall(NonRepudiationOfXu.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class ArbitrateEventResponse extends BaseEventResponse {
        public String ret;
    }

    public static class ClientConfirmEventResponse extends BaseEventResponse {
        public String ret;
    }

    public static class CloudDoRound1EventResponse extends BaseEventResponse {
        public String ret;
    }

    public static class CloudDoRound2EventResponse extends BaseEventResponse {
        public String ret;
    }

    public static class RequestServiceEventResponse extends BaseEventResponse {
        public String ret;
    }

    public static class TerminatedServiceEventResponse extends BaseEventResponse {
        public String ret;
    }
}
