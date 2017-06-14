package com.example.rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SampleController {

    @GetMapping('/v1/map')
    getMap() {
        [
                id    : '123',
                values: [
                        '456': [
                                id   : '456',
                                title: 'Item 456'
                        ],
                        '789': [
                                id   : '789',
                                title: 'Item 789'
                        ]
                ]
        ]
    }
}
