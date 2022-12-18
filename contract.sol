// Contract based on https://docs.openzeppelin.com/contracts/3.x/erc721
// SPDX-License-Identifier: MIT
// nic_nft.sol
pragma solidity ^0.8.0;

import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Enumerable.sol";
import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/utils/Strings.sol";

contract NicMeta is ERC721Enumerable, Ownable {
    using Strings for uint256;

    bool public _isSaleActive = true;
    bool public _revealed = true;

    // Constants
    uint256 public constant MAX_SUPPLY = 100;
    uint256 public mintPrice = 0.1 ether;
    uint256 public maxBalance = 10;
    uint256 public maxMint = 10;

    string baseURI  = "ipfs://QmZV8Mrd2v71SdMeNwaULzadM5m2JWkbf1Sjbo8WwkY7db/";
    // string public notRevealedUri;
    string public baseExtension = ".json";

    mapping(uint256 => string) private _tokenURIs;

    constructor(string memory initBaseURI, string memory initNotRevealedUri)
    ERC721("Dj Boy", "DB")
    {
        // setBaseURI(initBaseURI);
        // setNotRevealedURI(initNotRevealedUri);
    }
    // tokenQuantity:输入的数字
    // totalSupply： 返回合约存储的代币总量

    function mintNicMeta(uint256 tokenQuantity) public payable {
        require(
            totalSupply() + tokenQuantity <= MAX_SUPPLY,
            "Sale would exceed max supply"
        );
        require(_isSaleActive, "Sale must be active to mint NicMetas");
        require(
            balanceOf(msg.sender) + tokenQuantity <= maxBalance,
            "Sale would exceed max balance"
        );
        require(
            tokenQuantity * mintPrice <= msg.value,
            "Not enough ether sent"
        );
        require(tokenQuantity <= maxMint, "Can only mint 1 tokens at a time");

        _mintNicMeta(tokenQuantity);
    }
    // _safeMint: 安全铸造tokenId并将其转移到to
    function _mintNicMeta(uint256 tokenQuantity) internal {
        for (uint256 i = 0; i < tokenQuantity; i++) {
            uint256 mintIndex = totalSupply();
            if (totalSupply() < MAX_SUPPLY) {
                _safeMint(msg.sender, mintIndex);
            }
        }
    }
    // tokenURI: 返回tokenId令牌的统一资源标识符 (URI)
    function tokenURI(uint256 tokenId)
    public
    view
    virtual
    override
    returns (string memory)
    {
        require(
            _exists(tokenId),
            "ERC721Metadata: URI query for nonexistent token"
        );

        // if (_revealed == false) {
        //     return notRevealedUri;
        // }

        string memory _tokenURI = _tokenURIs[tokenId];
        string memory base = _baseURI();

        // If there is no base URI, return the token URI.
        if (bytes(base).length == 0) {
            return _tokenURI;
        }
        // If both are set, concatenate the baseURI and tokenURI (via abi.encodePacked).
        if (bytes(_tokenURI).length > 0) {
            return string(abi.encodePacked(base, _tokenURI));
        }
        // If there is a baseURI but no tokenURI, concatenate the tokenID to the baseURI.
        return
        string(abi.encodePacked(base, tokenId.toString(), baseExtension));
    }





    // internal
    function _baseURI() internal view virtual override returns (string memory) {
        return baseURI;
    }

    //only owner
    function flipSaleActive() public onlyOwner {
        _isSaleActive = !_isSaleActive;
    }


    //盲盒开关
    function flipReveal() public onlyOwner {
        _revealed = !_revealed;
    }

    function setMintPrice(uint256 _mintPrice) public onlyOwner {
        mintPrice = _mintPrice;
    }
    //设定盲盒位置
    // function setNotRevealedURI(string memory _notRevealedURI) public onlyOwner {
    //     notRevealedUri = _notRevealedURI;
    // }

    function setBaseURI(string memory _newBaseURI) public onlyOwner {
        baseURI = _newBaseURI;
    }

    function setBaseExtension(string memory _newBaseExtension)
    public
    onlyOwner
    {
        baseExtension = _newBaseExtension;
    }

    function setMaxBalance(uint256 _maxBalance) public onlyOwner {
        maxBalance = _maxBalance;
    }

    function setMaxMint(uint256 _maxMint) public onlyOwner {
        maxMint = _maxMint;
    }

    function withdraw(address to) public onlyOwner {
        uint256 balance = address(this).balance;
        payable(to).transfer(balance);
    }
}







// Contract based on https://docs.openzeppelin.com/contracts/3.x/erc721
// SPDX-License-Identifier: MIT
// metazoon_nft.sol
pragma solidity ^0.8.0;

import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Enumerable.sol";
import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/utils/Strings.sol";

contract MetazoonNFT is ERC721Enumerable, Ownable {
    using Strings for uint256;

    bool public _isSaleActive = false;
    bool public _revealed = false;

    // Constants
    uint256 public constant MAX_SUPPLY = 10;
    uint256 public mintPrice = 0.3 ether;
    uint256 public maxBalance = 1;
    uint256 public maxMint = 1;

    string baseURI;
    string public notRevealedUri;
    string public baseExtension = ".json";

    mapping(uint256 => string) private _tokenURIs;

    constructor(string memory initBaseURI, string memory initNotRevealedUri)
    ERC721("MetazoonNFT", "MTZ-NFT")
    {
        setBaseURI(initBaseURI);
        setNotRevealedURI(initNotRevealedUri);
    }

    function mintMetazoonNFT(uint256 tokenQuantity) public payable {
        require(
            totalSupply() + tokenQuantity <= MAX_SUPPLY,
            "Sale would exceed max supply"
        );
        require(_isSaleActive, "Sale must be active to mint MetazoonNFT");
        require(
            balanceOf(msg.sender) + tokenQuantity <= maxBalance,
            "Sale would exceed max balance"
        );
        require(
            tokenQuantity * mintPrice <= msg.value,
            "Not enough ether sent"
        );
        require(tokenQuantity <= maxMint, "Can only mint 1 tokens at a time");

        _mintMetazoonNFT(tokenQuantity);
    }

    function _mintMetazoonNFT(uint256 tokenQuantity) internal {
        for (uint256 i = 0; i < tokenQuantity; i++) {
            uint256 mintIndex = totalSupply();
            if (totalSupply() < MAX_SUPPLY) {
                _safeMint(msg.sender, mintIndex);
            }
        }
    }

    function tokenURI(uint256 tokenId)
    public
    view
    virtual
    override
    returns (string memory)
    {
        require(
            _exists(tokenId),
            "ERC721Metadata: URI query for nonexistent token"
        );

        if (_revealed == false) {
            return notRevealedUri;
        }

        string memory _tokenURI = _tokenURIs[tokenId];
        string memory base = _baseURI();

        // If there is no base URI, return the token URI.
        if (bytes(base).length == 0) {
            return _tokenURI;
        }
        // If both are set, concatenate the baseURI and tokenURI (via abi.encodePacked).
        if (bytes(_tokenURI).length > 0) {
            return string(abi.encodePacked(base, _tokenURI));
        }
        // If there is a baseURI but no tokenURI, concatenate the tokenID to the baseURI.
        return
        string(abi.encodePacked(base, tokenId.toString(), baseExtension));
    }

    // internal
    function _baseURI() internal view virtual override returns (string memory) {
        return baseURI;
    }

    //only owner
    function flipSaleActive() public onlyOwner {
        _isSaleActive = !_isSaleActive;
    }

    function flipReveal() public onlyOwner {
        _revealed = !_revealed;
    }

    function setMintPrice(uint256 _mintPrice) public onlyOwner {
        mintPrice = _mintPrice;
    }

    function setNotRevealedURI(string memory _notRevealedURI) public onlyOwner {
        notRevealedUri = _notRevealedURI;
    }

    function setBaseURI(string memory _newBaseURI) public onlyOwner {
        baseURI = _newBaseURI;
    }

    function setBaseExtension(string memory _newBaseExtension)
    public
    onlyOwner
    {
        baseExtension = _newBaseExtension;
    }

    function setMaxBalance(uint256 _maxBalance) public onlyOwner {
        maxBalance = _maxBalance;
    }

    function setMaxMint(uint256 _maxMint) public onlyOwner {
        maxMint = _maxMint;
    }

    function withdraw(address to) public onlyOwner {
        uint256 balance = address(this).balance;
        payable(to).transfer(balance);
    }
}






// SPDX-License-Identifier: MIT
// nft_01.sol
pragma solidity ^0.8.4;

import "@openzeppelin/contracts/token/ERC721/ERC721.sol";
import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Enumerable.sol";
import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/utils/Counters.sol";
import "@openzeppelin/contracts/utils/cryptography/MerkleProof.sol";


contract MyToken is ERC721, ERC721Enumerable, Ownable {
    using Counters for Counters.Counter;
    bytes32 public root;

    Counters.Counter private _tokenIdCounter;
    uint256 public mintRate = 0.000000001 ether;
    uint public MAX_SUPPLY = 10000;
    address[] public whitelistedAddresses;
    bool public onlyWhitelisted = true;


    constructor(bytes32 _root) ERC721("MyToken", "MTK") {
        root = _root;
    }


    function _baseURI() internal pure override returns (string memory) {
        return "ipfs://QmZV8Mrd2v71SdMeNwaULzadM5m2JWkbf1Sjbo8WwkY7db/";
    }

    function isValid(bytes32[] memory proof, bytes32 leaf) public view returns(bool){
        return MerkleProof.verify(proof, root, leaf);
    }

    function safeMint(address to, bytes32[] memory proof, bytes32 leaf) public payable{
        require(keccak256(abi.encodePacked(msg.sender)) == leaf, "Not the correct leaf");
        require(isValid(proof, leaf), " Not a part of AllowList!!");
        require(totalSupply() < MAX_SUPPLY, "Can't mint more!!!");
        require(msg.value >= mintRate, "Not enough ether send!!!");
        uint256 tokenId = _tokenIdCounter.current();
        _tokenIdCounter.increment();
        _safeMint(to, tokenId);
    }

    function whitelistUsers(address[] calldata _users) public onlyOwner{
        delete whitelistedAddresses;
        whitelistedAddresses = _users;
    }

    // The following functions are overrides required by Solidity.

    function _beforeTokenTransfer(address from, address to, uint256 tokenId)
    internal
    override(ERC721, ERC721Enumerable)
    {
        super._beforeTokenTransfer(from, to, tokenId);
    }

    function supportsInterface(bytes4 interfaceId)
    public
    view
    override(ERC721, ERC721Enumerable)
    returns (bool)
    {
        return super.supportsInterface(interfaceId);
    }

    function withdraw() public onlyOwner{
        require(address(this).balance > 0, "Balance is 0!!!");
        payable(owner()).transfer(address(this).balance);
    }
}










// SPDX-License-Identifier: MIT
// nft_02.sol
pragma solidity ^0.8.4;

import "@openzeppelin/contracts/token/ERC721/ERC721.sol";
import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Enumerable.sol";
import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/utils/Counters.sol";
import "@openzeppelin/contracts/utils/cryptography/MerkleProof.sol";
import "@openzeppelin/contracts/utils/Strings.sol";



contract MyToken is ERC721, ERC721Enumerable, Ownable {
    using Counters for Counters.Counter;

    Counters.Counter private _tokenIdCounter;
    uint256 public mintRate = 0.00000 ether;
    uint256 public nftPerAddressLimit = 5;
    uint256 public MAX_SUPPLY = 10000;
    address[] public whitelistedAddresses;
    string baseURI  = "http://gateway.pinata.cloud/ipfs/QmWHf8p49NPw4b6g26qsc8CVTtM1xHmMEzWojSv2ysbbaX/";
    string public baseExtension = ".json";
    mapping(uint256 => string) private _tokenURIs;
    bool public onlyWhitelisted = true;
    /**
    ["0x78731D3Ca6b7E34aC0F824c42a7cC18A495cabaB","0xC91BC3891bF0b1580c29DD370f8197Cdb6B109a2",
    "0x3aeEC44cA2D8b94c5d2CE9F06FCb6Efb7A76f93C","0x5B38Da6a701c568545dCfcB03FcB875f56beddC4"]
    "0x78731D3Ca6b7E34aC0F824c42a7cC18A495cabaB","0xC91BC3891bF0b1580c29DD370f8197Cdb6B109a2",
    "0x78731D3Ca6b7E34aC0F824c42a7cC18A495cabaB","0xC91BC3891bF0b1580c29DD370f8197Cdb6B109a2",
    "0x78731D3Ca6b7E34aC0F824c42a7cC18A495cabaB","0xC91BC3891bF0b1580c29DD370f8197Cdb6B109a2",
    "0x78731D3Ca6b7E34aC0F824c42a7cC18A495cabaB","0xC91BC3891bF0b1580c29DD370f8197Cdb6B109a2",
    "0x78731D3Ca6b7E34aC0F824c42a7cC18A495cabaB","0xC91BC3891bF0b1580c29DD370f8197Cdb6B109a2",
    "0x78731D3Ca6b7E34aC0F824c42a7cC18A495cabaB","0xC91BC3891bF0b1580c29DD370f8197Cdb6B109a2",]
    */

    constructor( )ERC721("Dj_Boy", "DB") {

    }

    function _baseURI() internal view virtual override returns (string memory) {
        return baseURI;
    }

    function tokenURI(uint256 tokenId)
    public
    view
    virtual
    override
    returns (string memory)
    {
        require(
            _exists(tokenId),
            "ERC721Metadata: URI query for nonexistent token"
        );

        string memory _tokenURI = _tokenURIs[tokenId];
        string memory base = _baseURI();

        // If there is no base URI, return the token URI.
        if (bytes(base).length == 0) {
            return _tokenURI;
        }
        // If both are set, concatenate the baseURI and tokenURI (via abi.encodePacked).
        if (bytes(_tokenURI).length > 0) {
            return string(abi.encodePacked(base, _tokenURI));
        }
        // If there is a baseURI but no tokenURI, concatenate the tokenID to the baseURI.
        return
        string(abi.encodePacked(base, Strings.toString(tokenId), baseExtension));
    }

    function safeMint() public payable{
        uint256 ownerTokenCount = balanceOf(msg.sender);
        uint256 tokenId = _tokenIdCounter.current();
        require(totalSupply() < MAX_SUPPLY, "Can't mint more!!");
        require(msg.value >= mintRate, "Not enough ether send!!!");
        require(isWhitelisted(msg.sender), "user is not whitelisted!!");
        require(ownerTokenCount < nftPerAddressLimit, "Can't mint more!!");
        _tokenIdCounter.increment();
        _safeMint(msg.sender, tokenId);
    }

    function isWhitelisted(address _user) public view returns(bool){
        for(uint256 i = 0; i < whitelistedAddresses.length; i++){
            if(whitelistedAddresses[i] == _user){
                return true;
            }
        }
        return false;
    }

    function whitelistUsers(address[] calldata _users) public onlyOwner{
        delete whitelistedAddresses;
        whitelistedAddresses = _users;
    }

    // The following functions are overrides required by Solidity.

    function _beforeTokenTransfer(address from, address to, uint256 tokenId)
    internal
    override(ERC721, ERC721Enumerable)
    {
        super._beforeTokenTransfer(from, to, tokenId);
    }

    function supportsInterface(bytes4 interfaceId)
    public
    view
    override(ERC721, ERC721Enumerable)
    returns (bool)
    {
        return super.supportsInterface(interfaceId);
    }

    function withdraw() public onlyOwner{
        require(address(this).balance > 0, "Balance is 0!!!");
        payable(owner()).transfer(address(this).balance);
    }
}



