package com.planetpulse.api.service;

import com.planetpulse.api.dto.request.RestCountriesPage;
import com.planetpulse.api.dto.request.RestCountryDto;
import com.planetpulse.api.exception.countries.ExternalApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestCountriesService {

    private static final Logger log = LoggerFactory.getLogger(RestCountriesService.class);

    private static final String FIELDS =
            "names.common,codes.alpha_2,flag.url_svg,region,subregion,area.kilometers,population";
    private static final int PAGE_SIZE = 100;

    private final RestClient restClient;

    public RestCountriesService(RestClient restCountriesRestClient) {
        this.restClient = restCountriesRestClient;
    }

    public List<RestCountryDto> fetchAll() {
        List<RestCountryDto> countries = new ArrayList<>();
        int offset = 0;
        int expectedTotal = -1; //it's not known until the API give it.
        boolean more = true;

        try {
            while (more) {
                final int currentOffset = offset;
                RestCountriesPage page = restClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/countries/v5")
                                .queryParam("response_fields", FIELDS)
                                .queryParam("limit", PAGE_SIZE)
                                .queryParam("offset", currentOffset)
                                .build())
                        .retrieve()
                        .body(RestCountriesPage.class);

                //Unexcepted response: clear error instead of a NullPointerException
                if(page == null || page.data() == null || page.data().objects() == null) {
                    throw new ExternalApiException("REST Countries returned an unexcepted response");
                }

                List<RestCountryDto> objects = page.data().objects();
                countries.addAll(objects);
                offset += objects.size();

                RestCountriesPage.Meta meta = page.data().meta();
                if (meta != null && meta.total() > 0) {
                    expectedTotal = meta.total();
                }

                //An empty page mean there is nothing left, even if "more" say true:
                more = !objects.isEmpty()
                        && (expectedTotal > 0 ? offset < expectedTotal : meta != null && meta.more());
            }
        } catch (RestClientException exception) {
            log.error("REST countries call has failed at offset {}", offset, exception);
            throw new ExternalApiException("Could not fetch the countries from REST Countries.", exception);
        }

        //To avoid the silent partial import error
        if (expectedTotal <= 0) {
            throw new ExternalApiException("REST Countries did not give the total: the import cannot be verified");
        }
        if (countries.size() != expectedTotal) {
            throw new ExternalApiException("Incomplete data from REST Countries: received"
                    + countries.size() + "of " + expectedTotal + " countries");
        }

        return countries;
    }
}
