# Spring Authorization Server

This project is a study guide to help you implement OAuth 2.0 using the Spring Security framework with the Authorization Code Flow grant type.

------

## OAuth 2.0 Basic Concepts

Before diving into the flow of the application, it's important to understand some basic concepts of OAuth 2.0:

- **OAuth 2.0 Goal**: OAuth allows third-party applications to have limited access to resources hosted by another application.

------

### Key Roles in OAuth 2.0:
- **Resource Owner**: The user who wants to access the resource.
- **Client**: The application requesting access on behalf of the user.
- **Authorization Server**: The entity responsible for receiving credentials and granting access (or not).
- **Resource Server**: The server that hosts the protected resources (e.g., API endpoints).

------

### OAuth 2.0 Flow:
1. The **Resource Owner** (user) tries to access a protected resource (e.g., the `/tasks` endpoint) through the **Client** application.
2. The **Client** application redirects the user to the **Authorization Server** (e.g., Google, Facebook, or another Identity Provider).
3. The **Authorization Server** requests the user's credentials, and the user enters them. Note that the **Client** application never sees these credentials, only the **Authorization Server**.
4. If the credentials are valid, the **Authorization Server** issues an access token to the **Client** application, which can then use the token to access the protected resource from the **Resource Server**.
------

## Spring Authorization Server Architecture

In this project, we have three key applications:

1. **auth-server**: Responsible for authorizing clients and users. It receives credentials and generates access tokens. All clients and users who want access must be registered with the auth-server.
2. **client-server**: A service representing the client application that requests access to protected resources.
3. **resource-server**: The service that hosts the protected resources and validates access tokens. It communicates with the **auth-server** to decode and verify tokens.

------

## Spring Authorization Server Flow

The execution flow in this project follows these steps:

1. The **client-server** application initiates a request to the protected resource (e.g., `/tasks`) and provides the **client-id** and **redirect-uri**.
2. Since the `/tasks` endpoint is protected, the request is redirected to the **auth-server**.
3. The **auth-server** prompts the **resource owner** (user) to enter their credentials.
4. Upon successful credential input, the **auth-server** generates an authorization code and sends it back to the **client-server** through the previously provided **redirect-uri**.
5. The **client-server** then sends the authorization code to the **auth-server** to request an access token.
6. Once the access token is generated, the **client-server** includes it in subsequent requests to the **resource-server** to access the protected resources.


