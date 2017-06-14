package com.example.rest

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.restdocs.JUnitRestDocumentation
import org.springframework.test.web.servlet.MockMvc

import static org.mockito.Mockito.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import static org.springframework.restdocs.payload.PayloadDocumentation.*
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*
import static org.springframework.restdocs.request.RequestDocumentation.*

class SampleControllerTest {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation('build/generated-snippets')

    MockMvc mockMvc

    SampleController controller = new SampleController()


    @Before
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper()
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter()
        converter.setObjectMapper(objectMapper)

        this.mockMvc = standaloneSetup(controller)
                .apply(documentationConfiguration(this.restDocumentation)
                .uris().withScheme('https').withHost('wild.example.com').withPort(443))
                .setMessageConverters(converter)
                .build()
    }

    @Test
    void "GET Map with variable keys"(){
        mockMvc.perform(get('/v1/map').accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                document('get-map',
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath('id').description('The base ID.'),
                                fieldWithPath('values').description('A Map of values keyed off their ID.'),
                                fieldWithPath('values.*.id').description('The ID for the Value.'),
                                fieldWithPath('values.*.title').description('The title for the Value.')
                        )
                )
        )
    }

}
