/**
 *Submitted for verification at Etherscan.io on 2021-01-19
*/

// SPDX-License-Identifier: MIT
pragma solidity ^0.6.0;

contract Launchpad{

    address private _cloud;
    
    address private _client;
    
    address private _arbitrator;
    
    uint256 private _cloudPenalty;
    
    uint256 private _clientServiceFee;
    
    int private _serviceStatus;
    
    bytes32 private _verifyResult;
    
    bytes32 private _hashOfS1;

    uint256 private _confirmStartTime;
    
    uint256 private _duringTime;
    
    uint256 private _s2PublishStartTime;
    
    string private _serviceName;
    
    string private _s2;
    
    uint256 private _claimStartTime;
    
    uint private _randomBlock;
    
    
    /**
    * @dev Emitted Received money sources  
    *
    */
    event Received(string from, uint256 value);
    
    /**
    * @dev Emitted that client requests the service
    *
    */
    event RequestService(string ret);
    
    /**
    * @dev Emitted that cloud publishes the verify result, hashOfS1, and transfer penatly
    *
    */
    event CloudDoRound1(string ret);
    
    /**
    * @dev Emitted that client confirms the hash of s1 and transfer the service fees
    *
    */
    event ClientConfirm(string ret);
    
  
    /**
    * @dev Emitted that client terminates the service if the hash(s1) is wrong.
    *   Or cloud terminates the service if client does not confirms hash(s1) in time.
    */
    event TerminatedService(string ret);
    
    /**
    * @dev Emitted that cloud publishes s2
    *
    */
    event CloudDoRound2(string ret);

    
    /**
    * @dev Emitted that cloud or the client launches the on-chain arbitration
    *
    */
    event Arbitrate(string ret);
    

    constructor(
        address cloud,
        address client,
        uint256 cloudPenalty,
        uint256 clientServiceFee,
        uint256 duringTime
    ) 
    public 
    {
        _cloud = cloud;
        _client = client;
        _arbitrator = msg.sender;
        
        _cloudPenalty = cloudPenalty;
        _clientServiceFee = clientServiceFee;
        
        _confirmStartTime = 0;
        _duringTime = duringTime;
        
        _s2PublishStartTime = 0;
        
        _claimStartTime = 0;
        
        _serviceStatus = 0;
    }
    
 
    
    /**
    * @dev Function to get details of project
    */
    function getBasicInfos() public view returns (
        address cloud,
        address client,
        address arbitrator,
        uint256 cloudPenalty,
        uint256 clientServiceFee
    ) {
        cloud = _cloud;
        client = _client;
        arbitrator = _arbitrator;
        cloudPenalty = _cloudPenalty;
        clientServiceFee = _clientServiceFee;
    }
    
    function getDetailsInfos() public view returns (
        string memory serviceName,
        bytes32 verifyResult,
        bytes32 hashOfS1,
        uint256 confirmStartTime,
        uint256 s2PublishStartTime,
        string memory s2,
        uint256 claimStartTime,
        uint256 duringTime,
        int serviceStatus,
        uint256 randomBlock
    ) {
        serviceName = _serviceName;
        verifyResult = _verifyResult;
        hashOfS1 = _hashOfS1;
        confirmStartTime = _confirmStartTime;
        s2PublishStartTime = _s2PublishStartTime;
        s2 = _s2;
        claimStartTime = _claimStartTime;
        duringTime = _duringTime;
        serviceStatus = _serviceStatus;
        randomBlock = _randomBlock;
    }
    
    /**
    * @dev Client requests the service
    *
    */
    function requestService(string memory serviceName) public {
        require(msg.sender == _client, "Should be the correct client address");
        require(_serviceStatus == 0, "Service already started or finished");
        _serviceName = serviceName;
        _serviceStatus = 1;
        emit RequestService('Client Requests Service');
    }
    
    /**
    * cloud publishes the verify result, hashOfS1, and transfer penatly
    *
    */
    function cloudDoRound1(bytes32 verifyResult, bytes32 hashOfS1) payable public {
        require(msg.sender == _cloud, "Should be the correct cloud address");
        require(_serviceStatus == 1, "Error service sequence");
        require(msg.value == _cloudPenalty, 'Error penatly amount');
            
        address(this).transfer(msg.value);
       
        _verifyResult = verifyResult;
        _hashOfS1 = hashOfS1;
        _serviceStatus = 2;
        _confirmStartTime = now;
        emit CloudDoRound1("Cloud do Round1");
    }
    
    
    /**
    * client confirms the hash of s1 and transfer the service fees
    *
    */
    function clientConfirm() payable public {
        require(msg.sender == _client, "Should be the correct client address");
        require(_serviceStatus == 2, "Error service sequence");
        require(now <= _confirmStartTime + _duringTime, 'The confirm time is over');
        require(msg.value == _clientServiceFee, 'Error service fees');
        
        address(this).transfer(msg.value);
        _serviceStatus = 3;
        _s2PublishStartTime = now;
        emit ClientConfirm('Client confirms');
    }
    
    /**
    * Emitted that cloud terminates the service if client does not confirms hash(s1) in time.
    *  Or client terminates the service if cloud does not publishes s2 in time.
    */
    function terminatedService() public {
        require(msg.sender == _cloud || msg.sender == _client, "Should be the correct address");
        
        if (msg.sender == _cloud) {
            require(_serviceStatus == 2, "Error service sequence");
            require(now > _confirmStartTime + _duringTime, 'The confirm time is not over');
            _serviceStatus = -1;
            // return the money of Cloud
            payable(_cloud).transfer(_cloudPenalty);
            emit TerminatedService("Cloud Terminate Service");
        }
        if (msg.sender == _client) {
            require(_serviceStatus == 3, "Error service sequence");
            require(now > _s2PublishStartTime + _duringTime, 'The publish time of s2 is not over');
            
            _serviceStatus = -1;
            
            // return the money of client and cloud
            payable(_client).transfer(_clientServiceFee);
            payable(_cloud).transfer(_cloudPenalty);
            emit TerminatedService("Client Terminate Service");
        }
    }
    
    
    /**
    * @dev cloud publishes s2
    *
    */
    function cloudDoRound2(string memory s2, uint256 randomBlock) public {
        require(msg.sender == _cloud, "Should be the correct cloud address");
        require(_serviceStatus == 3, "Error service sequence");
        
        if (now > _s2PublishStartTime + _duringTime) {
            _serviceStatus = -1;
            // return the money of client and cloud
            payable(_cloud).transfer(_cloudPenalty);
            payable(_client).transfer(_clientServiceFee);
            emit CloudDoRound2("The publish time of s2 is over");
        }
        else {
            _s2 = s2;
            _randomBlock = randomBlock;
            _serviceStatus = 4;
            _claimStartTime = now;
            emit CloudDoRound2('Cloud do Round2');   
        }
    }
    
    
    function arbitrate() public { // client
        require(msg.sender == _client, 'Should be the correct client address');
        require(_serviceStatus == 4, "Error service sequence");
        require(now <= _claimStartTime + _duringTime, 'Arbitration time is over');
        
        bytes32 hash_s2 = sha256(bytes(_s2));
        
        if (_hashOfS1 ^ hash_s2 == _verifyResult) {
            emit Arbitrate('On-chain Result: Malicious Client');   
        }
        
        else {
            _serviceStatus = -1;
            payable(_client).transfer(_cloudPenalty+_clientServiceFee);
            emit Arbitrate('On-chain Result: Malicious SP');
        }
    }
    
    
    function lauchoffChainArbitrate() public { // client
        require (msg.sender == _client, 'Should be the correct client address');
        require(_serviceStatus == 4, "Error service sequence");
        require(now <= _claimStartTime + _duringTime, 'Arbitration time is over');
        
        _serviceStatus = 5;
        emit Arbitrate('Client Launches Off-chain Arbitration');
    }
    
    
    /**
    * @dev cloud claim service fees and penatly after claim time started
    *
    */
    function claim() public {
        require(msg.sender == _cloud, 'Should be the correct cloud address');
        require(_serviceStatus == 4, "Error service sequence");
        require(now > _claimStartTime + _duringTime, 'Claim time is not start');
        
        
        // return the money 
        _serviceStatus = -1;
        payable(_cloud).transfer(_cloudPenalty+_clientServiceFee);
        
    }
    
    
    /**
    * @dev off-chain arbitrate result: 0: Malicious client, 1: Malicious client
    *
    */
    function offChainArbitrate(uint256 result) public {
        require(msg.sender == _arbitrator);
        require(_serviceStatus == 5, "Error service sequence");
        
        if (result == 0) {
            // return all the money to cloud
            _serviceStatus = -1;
            payable(_cloud).transfer(_cloudPenalty+_clientServiceFee);
            emit Arbitrate('Off-chain Result: Malicious Client');   
        }
        else {
            // return all the money to client
            _serviceStatus = -1;
            payable(_client).transfer(_cloudPenalty+_clientServiceFee);
            emit Arbitrate('Off-chain Result: Malicious Cloud');   
        }
    }
    
    function getHashOfS1() view public returns(bytes32) {
        return _hashOfS1;
    }
    
    function getS2() view public returns(string memory) {
        return _s2;
    }
    
    function getRandomBlock() view public returns(uint256) {
        return _randomBlock;
    }
    
    function getVerifyResult() view public returns(bytes32) {
        return _verifyResult;
    }
    
    
    function getBalance() view public returns(uint) {
        return address(this).balance;
    }
    
    function restart() public {
        require(_serviceStatus == -1 && msg.sender == _arbitrator);
        _serviceStatus = 0;
    }
    
    /**
    * @dev Receive cloud's penatly or client's service fees.
    */
    receive() external payable {
    }
   
}
