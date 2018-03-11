const fs = require("fs-extra");
const path = require("path");
const HDWalletProvider = require("truffle-hdwallet-provider");
const Web3 = require("web3");

const compiledContract = require("./build/polAction2.json"); // get compiled bytecode and ABI (interface)

/* Need the following arguments for the provider instantiation below:
    1. Get the mnemonic phrase you should have recieved when you started up MetaMask
        Steps: install MetaMask from the Google Chrome store, create an account, you will be given a mnemonic phrase - save it
    2. Get the Rinkeby Test Provider URL from Infura which you should have recieved in an email after signing up
        Steps: sign up for Infura at https://infura.io/, you should recieve an email will all the public ethereum networks - save the Rinkeby URL
*/
const provider = new HDWalletProvider(
  "yard below guitar theme peasant feature junk badge vicious opera table ketchup", //mnemonic generates many accounts
  "https://rinkeby.infura.io/TG5RgdTVjGkNGhMiTow1" //provider url with access key
);
const web3 = new Web3(provider);

const deploy = async () => {
  const accounts = await web3.eth.getAccounts();

  console.log("Attempting to deploy contract from account", accounts[0]);

  const result = await new web3.eth.Contract(
    JSON.parse(compiledContract.interface)
  )
    .deploy({
      data: compiledContract.bytecode,
      arguments: ["86400", "0xdB876A1BAfCFD0a5C9DC647fAD03e7B1c440c96C", "500"]
    })
    .send({ gas: "5000000", from: accounts[0] });

  console.log("Contract deployed to", result.options.address);
  const pathToAddressFile = path.resolve(__dirname, "../", "ADDRESS");
  fs.outputFileSync(
    pathToAddressFile,
    "Contract deployed to " + result.options.address
  );
};

deploy();
