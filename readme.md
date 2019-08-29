## API to maintain simple address book

### Build Environment
    * JDK 1.8
    * gradle
    * Spring boot
    
### To build
  ```
    git clone git@github.com:arulthayalan/address-book-api.git
    
   ./gradlew clean build
  
  ```
### To run
   ```
    ./gradlew bootrun
   
   ```
#### API documentation
To view address book api swagger documentation [Swagger UI](http://localhost:8080/address-book-api/swagger-ui.html).
To download swagger json [Swagger JSON](http://localhost:8080/address-book-api/v2/api-docs)

## Request & Response Examples

### API Resources

- [POST /addressbook](#post-addressbook)
- [GET /addressbooks](#get-addressbooks)
- [GET /addressbooks/contacts/[queryfilter]](#get-addressbookscontactsqueryfilter)
    
### POST /addressbook

Example: Create – POST http://localhost:8080/address-book-api/addressbook

Request body:
```
    {
      "name": "book1",
      "phoneContacts": [
        {
          "name": "Bob",
          "mobileNumber": "0431056098"
        },
        {
          "name": "Mary",
          "mobileNumber": "042307841"
        },
        {
          "name": "Jane",
          "mobileNumber": "042307529"
        }
    
      ]
    }
```
### GET /addressbooks

Example: http://localhost:8080/address-book-api/addressbooks

Response body:
```
    {
      "data": {
        "name": "book1",
        "phoneContacts": [
          {
            "name": "Bob",
            "mobileNumber": "0431056098"
          },
          {
            "name": "Mary",
            "mobileNumber": "042307841"
          },
          {
            "name": "Jane",
            "mobileNumber": "042307529"
          }
        ]
      }
    }
```
### GET /addressbooks/contacts/[queryfilter]

Example: http://localhost:8080/address-book-api/addressbooks/contacts/UNIQUE

Example: http://localhost:8080/address-book-api/addressbooks/contacts/ALL

Response body:

```
    {
      "data": {
        "phoneContacts": [
          {
            "name": "Bob",
            "mobileNumber": "0431056098"
          },
          {
            "name": "Jane",
            "mobileNumber": "042307529"
          },
          {
            "name": "Mary",
            "mobileNumber": "042307841"
          }
        ]
      }
    }
```


    
  
    
