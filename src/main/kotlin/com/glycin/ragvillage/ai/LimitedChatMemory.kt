package com.glycin.ragvillage.ai

import dev.langchain4j.memory.chat.ChatMemoryProvider
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import java.util.function.Supplier

class LimitedChatMemory: Supplier<ChatMemoryProvider> {
    override fun get(): ChatMemoryProvider {
        return ChatMemoryProvider {
            MessageWindowChatMemory.withMaxMessages(2) // TODO: DO NET SET THIS TO 1, IT WILL REMOVE THE USER MESSAGE IF U ALSO HAVE A SYSTEM MESSAGE
        }
    }

}
