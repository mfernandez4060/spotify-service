# Spotyfy Service

This is a proxy service, it consumes the services configured in the application, avoiding the CORS error in the user interface that consumes the services of sites other than those of its host.
It works as a service pipeline between the user interface and backend services

## Index

+ **GET /api/v1/version**

+ **GET /api/v1/proxy**


## Examples Api Restful

#### In the urlRequest parameter, the url is placed, which is sent to the proxied service, encoded in Base64

curl -X GET "https://spotify-service-ui.herokuapp.com/api/v1/proxy?urlRequest=YXJ0aXN0cy83QW40eXZGN2hEWURvbE40bTV6S0Jw"
```json
{
  "external_urls" : {
    "spotify" : "https://open.spotify.com/artist/7An4yvF7hDYDolN4m5zKBp"
  },
  "followers" : {
    "href" : null,
    "total" : 3814230
  },
  "genres" : [ "argentine rock", "latin alternative", "latin rock", "rock en espanol", "ska argentino" ],
  "href" : "https://api.spotify.com/v1/artists/7An4yvF7hDYDolN4m5zKBp",
  "id" : "7An4yvF7hDYDolN4m5zKBp",
  "images" : [ {
    "height" : 640,
    "url" : "https://i.scdn.co/image/24cf887715b0f35ef56b01b3127bc85486485155",
    "width" : 640
  }, {
    "height" : 320,
    "url" : "https://i.scdn.co/image/f6de30df794fbfc3ede641c04a70b8b30b178035",
    "width" : 320
  }, {
    "height" : 160,
    "url" : "https://i.scdn.co/image/27109e0a177218b636bfef81e6dc455cbe34ccac",
    "width" : 160
  } ],
  "name" : "Soda Stereo",
  "popularity" : 76,
  "type" : "artist",
  "uri" : "spotify:artist:7An4yvF7hDYDolN4m5zKBp"
}
```