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
import org.web3j.abi.datatypes.generated.Bytes32;
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
import org.web3j.tuples.generated.Tuple10;
import org.web3j.tuples.generated.Tuple5;
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
public class NonRepudiation extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051612739380380612739833981810160405260a081101561003357600080fd5b810190808051906020019092919080519060200190929190805190602001909291908051906020019092919080519060200190929190505050846000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555083600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555033600260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555082600381905550816004819055506000600881905550806009819055506000600a819055506000600d81905550600060058190555050505050506125c2806101776000396000f3fe6080604052600436106101025760003560e01c806350df52bc1161009557806399ee27b51161006457806399ee27b5146104025780639f4107381461048b578063a02da75f146105bf578063b5257746146105ea578063c1caaee61461060157610109565b806350df52bc14610367578063794f24f3146103925780637b78303a146103cd57806393fa4c16146103f857610109565b8063219cabf3116100d1578063219cabf3146102395780632cbdc10014610271578063472b370d146103395780634e71d92d1461035057610109565b8063019015581461010e578063024c9e3b146101e057806312065fe0146101f75780631ef3755d1461022257610109565b3661010957005b600080fd5b34801561011a57600080fd5b506101de6004803603604081101561013157600080fd5b810190808035906020019064010000000081111561014e57600080fd5b82018360208201111561016057600080fd5b8035906020019184600183028401116401000000008311171561018257600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050919291929080359060200190929190505050610691565b005b3480156101ec57600080fd5b506101f56109bd565b005b34801561020357600080fd5b5061020c610fa8565b6040518082815260200191505060405180910390f35b34801561022e57600080fd5b50610237610fb0565b005b61026f6004803603604081101561024f57600080fd5b810190808035906020019092919080359060200190929190505050611041565b005b34801561027d57600080fd5b506103376004803603602081101561029457600080fd5b81019080803590602001906401000000008111156102b157600080fd5b8201836020820111156102c357600080fd5b803590602001918460018302840111640100000000831117156102e557600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192905050506112a4565b005b34801561034557600080fd5b5061034e61142f565b005b34801561035c57600080fd5b506103656117d4565b005b34801561037357600080fd5b5061037c611a01565b6040518082815260200191505060405180910390f35b34801561039e57600080fd5b506103cb600480360360208110156103b557600080fd5b8101908080359060200190929190505050611a0b565b005b3480156103d957600080fd5b506103e2611c60565b6040518082815260200191505060405180910390f35b610400611c6a565b005b34801561040e57600080fd5b50610417611f3b565b604051808673ffffffffffffffffffffffffffffffffffffffff1681526020018573ffffffffffffffffffffffffffffffffffffffff1681526020018473ffffffffffffffffffffffffffffffffffffffff1681526020018381526020018281526020019550505050505060405180910390f35b34801561049757600080fd5b506104a0611fc2565b60405180806020018b81526020018a81526020018981526020018881526020018060200187815260200186815260200185815260200184815260200183810383528d818151815260200191508051906020019080838360005b838110156105145780820151818401526020810190506104f9565b50505050905090810190601f1680156105415780820380516001836020036101000a031916815260200191505b50838103825288818151815260200191508051906020019080838360005b8381101561057a57808201518184015260208101905061055f565b50505050905090810190601f1680156105a75780820380516001836020036101000a031916815260200191505b509c5050505050505050505050505060405180910390f35b3480156105cb57600080fd5b506105d4612140565b6040518082815260200191505060405180910390f35b3480156105f657600080fd5b506105ff61214a565b005b34801561060d57600080fd5b50610616612338565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561065657808201518184015260208101905061063b565b50505050905090810190601f1680156106835780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610735576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260238152602001806124e06023913960400191505060405180910390fd5b6003600554146107ad576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260168152602001807f4572726f7220736572766963652073657175656e63650000000000000000000081525060200191505060405180910390fd5b600954600a5401421115610923577fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff60058190555060008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc6003549081150290604051600060405180830381858888f1935050505015801561084a573d6000803e3d6000fd5b50600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc6004549081150290604051600060405180830381858888f193505050501580156108b5573d6000803e3d6000fd5b507ffbb37fef1e24d6f05773bdb0b2f48507b92149924d04bb48bfd1ca4fb23778ae60405180806020018281038252601e8152602001807f546865207075626c6973682074696d65206f66207332206973206f766572000081525060200191505060405180910390a16109b9565b81600c90805190602001906109399291906123da565b5080600e81905550600460058190555042600d819055507ffbb37fef1e24d6f05773bdb0b2f48507b92149924d04bb48bfd1ca4fb23778ae60405180806020018281038252600f8152602001807f436c6f756420646f20526f756e6432000000000000000000000000000000000081525060200191505060405180910390a15b5050565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161480610a645750600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16145b610ad6576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601d8152602001807f53686f756c642062652074686520636f7272656374206164647265737300000081525060200191505060405180910390fd5b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415610d1657600260055414610ba2576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260168152602001807f4572726f7220736572766963652073657175656e63650000000000000000000081525060200191505060405180910390fd5b600954600854014211610c1d576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601c8152602001807f54686520636f6e6669726d2074696d65206973206e6f74206f7665720000000081525060200191505060405180910390fd5b7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff60058190555060008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc6003549081150290604051600060405180830381858888f19350505050158015610cac573d6000803e3d6000fd5b507fbe71414413aef46f2b154f7d58e2d89c07fbc85711d6cd859dc67a6265cf3d1d6040518080602001828103825260178152602001807f436c6f7564205465726d696e617465205365727669636500000000000000000081525060200191505060405180910390a15b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415610fa657600360055414610de4576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260168152602001807f4572726f7220736572766963652073657175656e63650000000000000000000081525060200191505060405180910390fd5b600954600a54014211610e42576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260228152602001806124be6022913960400191505060405180910390fd5b7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff600581905550600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc6004549081150290604051600060405180830381858888f19350505050158015610ed3573d6000803e3d6000fd5b5060008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc6003549081150290604051600060405180830381858888f19350505050158015610f3c573d6000803e3d6000fd5b507fbe71414413aef46f2b154f7d58e2d89c07fbc85711d6cd859dc67a6265cf3d1d6040518080602001828103825260188152602001807f436c69656e74205465726d696e6174652053657276696365000000000000000081525060200191505060405180910390a15b565b600047905090565b7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff60055414801561102e5750600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16145b61103757600080fd5b6000600581905550565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146110e5576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260238152602001806124e06023913960400191505060405180910390fd5b60016005541461115d576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260168152602001807f4572726f7220736572766963652073657175656e63650000000000000000000081525060200191505060405180910390fd5b60035434146111d4576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260148152602001807f4572726f722070656e61746c7920616d6f756e7400000000000000000000000081525060200191505060405180910390fd5b3073ffffffffffffffffffffffffffffffffffffffff166108fc349081150290604051600060405180830381858888f1935050505015801561121a573d6000803e3d6000fd5b5081600681905550806007819055506002600581905550426008819055507f71a779111dd608222f0ee381004288552151c258cadd96876bd7a684ba5ff5df60405180806020018281038252600f8152602001807f436c6f756420646f20526f756e6431000000000000000000000000000000000081525060200191505060405180910390a15050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461134a576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252602481526020018061249a6024913960400191505060405180910390fd5b6000600554146113a5576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260238152602001806125036023913960400191505060405180910390fd5b80600b90805190602001906113bb9291906123da565b5060016005819055507f4b6d27f6fb159cec561279ade024f7ceb0509e8fb98a40f38cd849de504ac3376040518080602001828103825260178152602001807f436c69656e74205265717565737473205365727669636500000000000000000081525060200191505060405180910390a150565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146114d5576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252602481526020018061249a6024913960400191505060405180910390fd5b60046005541461154d576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260168152602001807f4572726f7220736572766963652073657175656e63650000000000000000000081525060200191505060405180910390fd5b600954600d54014211156115c9576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260188152602001807f4172626974726174696f6e2074696d65206973206f766572000000000000000081525060200191505060405180910390fd5b60006002600c604051808280546001816001161561010002031660029004801561162a5780601f1061160857610100808354040283529182019161162a565b820191906000526020600020905b815481529060010190602001808311611616575b5050915050602060405180830381855afa15801561164c573d6000803e3d6000fd5b5050506040513d602081101561166157600080fd5b81019080805190602001909291905050509050600654816007541814156116d2577f8b90e5ec748407584a6acd2b543d5e057bcc41b36b06d20cfe0a07eb317d2edd60405180806020018281038252602181526020018061256c6021913960400191505060405180910390a16117d1565b7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff600581905550600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc600454600354019081150290604051600060405180830381858888f19350505050158015611767573d6000803e3d6000fd5b507f8b90e5ec748407584a6acd2b543d5e057bcc41b36b06d20cfe0a07eb317d2edd60405180806020018281038252601d8152602001807f4f6e2d636861696e20526573756c743a204d616c6963696f757320535000000081525060200191505060405180910390a15b50565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614611878576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260238152602001806124e06023913960400191505060405180910390fd5b6004600554146118f0576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260168152602001807f4572726f7220736572766963652073657175656e63650000000000000000000081525060200191505060405180910390fd5b600954600d5401421161196b576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260178152602001807f436c61696d2074696d65206973206e6f7420737461727400000000000000000081525060200191505060405180910390fd5b7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff60058190555060008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc600454600354019081150290604051600060405180830381858888f193505050501580156119fe573d6000803e3d6000fd5b50565b6000600e54905090565b600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614611a6557600080fd5b6005805414611adc576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260168152602001807f4572726f7220736572766963652073657175656e63650000000000000000000081525060200191505060405180910390fd5b6000811415611ba25760008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc600454600354019081150290604051600060405180830381858888f19350505050158015611b51573d6000803e3d6000fd5b507f8b90e5ec748407584a6acd2b543d5e057bcc41b36b06d20cfe0a07eb317d2edd6040518080602001828103825260228152602001806124786022913960400191505060405180910390a1611c5d565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc600454600354019081150290604051600060405180830381858888f19350505050158015611c10573d6000803e3d6000fd5b507f8b90e5ec748407584a6acd2b543d5e057bcc41b36b06d20cfe0a07eb317d2edd60405180806020018281038252602181526020018061254b6021913960400191505060405180910390a15b50565b6000600754905090565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614611d10576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252602481526020018061249a6024913960400191505060405180910390fd5b600260055414611d88576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260168152602001807f4572726f7220736572766963652073657175656e63650000000000000000000081525060200191505060405180910390fd5b60095460085401421115611e04576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260188152602001807f54686520636f6e6669726d2074696d65206973206f766572000000000000000081525060200191505060405180910390fd5b6004543414611e7b576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260128152602001807f4572726f7220736572766963652066656573000000000000000000000000000081525060200191505060405180910390fd5b3073ffffffffffffffffffffffffffffffffffffffff166108fc349081150290604051600060405180830381858888f19350505050158015611ec1573d6000803e3d6000fd5b50600360058190555042600a819055507f8b0736a3e4227aa6e5120402894f2c2cd2c6e983e96b8d22d4560a33a8cfe08260405180806020018281038252600f8152602001807f436c69656e7420636f6e6669726d73000000000000000000000000000000000081525060200191505060405180910390a1565b60008060008060008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169450600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169350600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169250600354915060045490509091929394565b60606000806000806060600080600080600b8054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156120685780601f1061203d57610100808354040283529160200191612068565b820191906000526020600020905b81548152906001019060200180831161204b57829003601f168201915b50505050509950600654985060075497506008549650600a549550600c8054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156121195780601f106120ee57610100808354040283529160200191612119565b820191906000526020600020905b8154815290600101906020018083116120fc57829003601f168201915b50505050509450600d54935060095492506005549150600e54905090919293949596979899565b6000600654905090565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146121f0576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252602481526020018061249a6024913960400191505060405180910390fd5b600460055414612268576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260168152602001807f4572726f7220736572766963652073657175656e63650000000000000000000081525060200191505060405180910390fd5b600954600d54014211156122e4576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260188152602001807f4172626974726174696f6e2074696d65206973206f766572000000000000000081525060200191505060405180910390fd5b600580819055507f8b90e5ec748407584a6acd2b543d5e057bcc41b36b06d20cfe0a07eb317d2edd6040518080602001828103825260258152602001806125266025913960400191505060405180910390a1565b6060600c8054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156123d05780601f106123a5576101008083540402835291602001916123d0565b820191906000526020600020905b8154815290600101906020018083116123b357829003601f168201915b5050505050905090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061241b57805160ff1916838001178555612449565b82800160010185558215612449579182015b8281111561244857825182559160200191906001019061242d565b5b509050612456919061245a565b5090565b5b8082111561247357600081600090555060010161245b565b509056fe4f66662d636861696e20526573756c743a204d616c6963696f757320436c69656e7453686f756c642062652074686520636f727265637420636c69656e742061646472657373546865207075626c6973682074696d65206f66207332206973206e6f74206f76657253686f756c642062652074686520636f727265637420636c6f756420616464726573735365727669636520616c72656164792073746172746564206f722066696e6973686564436c69656e74204c61756e63686573204f66662d636861696e204172626974726174696f6e4f66662d636861696e20526573756c743a204d616c6963696f757320436c6f75644f6e2d636861696e20526573756c743a204d616c6963696f757320436c69656e74a26469706673582212209f8880027b4708e793571dc186e471f9e98bd26f6a26081b3707bb5d437943b364736f6c634300060c0033";

    public static final String FUNC_ARBITRATE = "arbitrate";

    public static final String FUNC_CLAIM = "claim";

    public static final String FUNC_CLIENTCONFIRM = "clientConfirm";

    public static final String FUNC_CLOUDDOROUND1 = "cloudDoRound1";

    public static final String FUNC_CLOUDDOROUND2 = "cloudDoRound2";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final String FUNC_GETBASICINFOS = "getBasicInfos";

    public static final String FUNC_GETDETAILSINFOS = "getDetailsInfos";

    public static final String FUNC_GETHASHOFS1 = "getHashOfS1";

    public static final String FUNC_GETRANDOMBLOCK = "getRandomBlock";

    public static final String FUNC_GETS2 = "getS2";

    public static final String FUNC_GETVERIFYRESULT = "getVerifyResult";

    public static final String FUNC_LAUCHOFFCHAINARBITRATE = "lauchoffChainArbitrate";

    public static final String FUNC_OFFCHAINARBITRATE = "offChainArbitrate";

    public static final String FUNC_REQUESTSERVICE = "requestService";

    public static final String FUNC_RESTART = "restart";

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

    public static final Event RECEIVED_EVENT = new Event("Received", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event REQUESTSERVICE_EVENT = new Event("RequestService", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event TERMINATEDSERVICE_EVENT = new Event("TerminatedService", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected NonRepudiation(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected NonRepudiation(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected NonRepudiation(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected NonRepudiation(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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

    public List<ReceivedEventResponse> getReceivedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(RECEIVED_EVENT, transactionReceipt);
        ArrayList<ReceivedEventResponse> responses = new ArrayList<ReceivedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ReceivedEventResponse typedResponse = new ReceivedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ReceivedEventResponse> receivedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ReceivedEventResponse>() {
            @Override
            public ReceivedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(RECEIVED_EVENT, log);
                ReceivedEventResponse typedResponse = new ReceivedEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ReceivedEventResponse> receivedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RECEIVED_EVENT));
        return receivedEventFlowable(filter);
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

    public RemoteFunctionCall<TransactionReceipt> claim() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLAIM, 
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

    public RemoteFunctionCall<TransactionReceipt> cloudDoRound1(byte[] verifyResult, byte[] hashOfS1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLOUDDOROUND1, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(verifyResult), 
                new org.web3j.abi.datatypes.generated.Bytes32(hashOfS1)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> cloudDoRound2(String s2, BigInteger randomBlock) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLOUDDOROUND2, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(s2), 
                new org.web3j.abi.datatypes.generated.Uint256(randomBlock)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getBalance() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple5<String, String, String, BigInteger, BigInteger>> getBasicInfos() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETBASICINFOS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple5<String, String, String, BigInteger, BigInteger>>(function,
                new Callable<Tuple5<String, String, String, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple5<String, String, String, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<String, String, String, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple10<String, byte[], byte[], BigInteger, BigInteger, String, BigInteger, BigInteger, BigInteger, BigInteger>> getDetailsInfos() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETDETAILSINFOS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Int256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple10<String, byte[], byte[], BigInteger, BigInteger, String, BigInteger, BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple10<String, byte[], byte[], BigInteger, BigInteger, String, BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple10<String, byte[], byte[], BigInteger, BigInteger, String, BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple10<String, byte[], byte[], BigInteger, BigInteger, String, BigInteger, BigInteger, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue(), 
                                (byte[]) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (String) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (BigInteger) results.get(7).getValue(), 
                                (BigInteger) results.get(8).getValue(), 
                                (BigInteger) results.get(9).getValue());
                    }
                });
    }

    public RemoteFunctionCall<byte[]> getHashOfS1() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETHASHOFS1, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<BigInteger> getRandomBlock() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETRANDOMBLOCK, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getS2() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETS2, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<byte[]> getVerifyResult() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETVERIFYRESULT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> lauchoffChainArbitrate() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_LAUCHOFFCHAINARBITRATE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> offChainArbitrate(BigInteger result) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_OFFCHAINARBITRATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(result)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> requestService(String serviceName) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REQUESTSERVICE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(serviceName)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> restart() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RESTART, 
                Arrays.<Type>asList(), 
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
    public static NonRepudiation load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new NonRepudiation(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static NonRepudiation load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new NonRepudiation(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static NonRepudiation load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new NonRepudiation(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static NonRepudiation load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new NonRepudiation(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<NonRepudiation> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String cloud, String client, BigInteger cloudPenalty, BigInteger clientServiceFee, BigInteger duringTime) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, cloud), 
                new org.web3j.abi.datatypes.Address(160, client), 
                new org.web3j.abi.datatypes.generated.Uint256(cloudPenalty), 
                new org.web3j.abi.datatypes.generated.Uint256(clientServiceFee), 
                new org.web3j.abi.datatypes.generated.Uint256(duringTime)));
        return deployRemoteCall(NonRepudiation.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<NonRepudiation> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String cloud, String client, BigInteger cloudPenalty, BigInteger clientServiceFee, BigInteger duringTime) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, cloud), 
                new org.web3j.abi.datatypes.Address(160, client), 
                new org.web3j.abi.datatypes.generated.Uint256(cloudPenalty), 
                new org.web3j.abi.datatypes.generated.Uint256(clientServiceFee), 
                new org.web3j.abi.datatypes.generated.Uint256(duringTime)));
        return deployRemoteCall(NonRepudiation.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<NonRepudiation> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String cloud, String client, BigInteger cloudPenalty, BigInteger clientServiceFee, BigInteger duringTime) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, cloud), 
                new org.web3j.abi.datatypes.Address(160, client), 
                new org.web3j.abi.datatypes.generated.Uint256(cloudPenalty), 
                new org.web3j.abi.datatypes.generated.Uint256(clientServiceFee), 
                new org.web3j.abi.datatypes.generated.Uint256(duringTime)));
        return deployRemoteCall(NonRepudiation.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<NonRepudiation> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String cloud, String client, BigInteger cloudPenalty, BigInteger clientServiceFee, BigInteger duringTime) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, cloud), 
                new org.web3j.abi.datatypes.Address(160, client), 
                new org.web3j.abi.datatypes.generated.Uint256(cloudPenalty), 
                new org.web3j.abi.datatypes.generated.Uint256(clientServiceFee), 
                new org.web3j.abi.datatypes.generated.Uint256(duringTime)));
        return deployRemoteCall(NonRepudiation.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
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

    public static class ReceivedEventResponse extends BaseEventResponse {
        public String from;

        public BigInteger value;
    }

    public static class RequestServiceEventResponse extends BaseEventResponse {
        public String ret;
    }

    public static class TerminatedServiceEventResponse extends BaseEventResponse {
        public String ret;
    }
}
