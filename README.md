# POSBillPrinterAPI
Java application to print bills based on the given order JSON  data

Opens a serversocket to recieve json data and print it using a connected printer.

Porper JSON formatting must be used.

The schema for JSON Order data is as follows:

{
"user": "Username",
"items": [
    {
    "id": "ID",
    "name": "Item Name",
    "price": "PRICE",
    "quantity": "QUANTITY"
    },...
  ]
}
