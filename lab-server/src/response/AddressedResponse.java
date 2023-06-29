package response;

import java.net.SocketAddress;

public record AddressedResponse(
        String serializedResponse,
        SocketAddress socketAddress
) {
}
