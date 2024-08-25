module io.portone.sdk.server {
    requires kotlinx.coroutines.core;
    requires kotlinx.serialization.json;
    requires io.ktor.client.core;
    requires io.ktor.client.okhttp;
    requires io.ktor.http;

    exports io.portone.sdk.server;
    exports io.portone.sdk.server.schemas;
    exports io.portone.sdk.server.webhook;
}
