package com.caio.vrc.resource;

import static com.caio.vrc.util.Patterns.NUMERIC_PATTERN;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.BeanParam;
import javax.ws.rs.PathParam;

import com.caio.vrc.resource.domain.PropertiesResponse;
import com.caio.vrc.resource.domain.PropertyFilterParam;
import com.caio.vrc.resource.domain.PropertyRequest;
import com.caio.vrc.resource.domain.PropertyResponse;

public interface PropertiesApi {

	/**
	 * @apiVersion 0.0.1
	 * @api {get} /v1/properties/{id} Request a property info by id
	 * @apiDescription Request a property info by id
	 * @apiName GetPropertyById
	 * @apiGroup Properties
	 * 
	 * @apiParam {Number} id Property unique ID.
	 * @apiSuccessExample Success-Response:
	 * 	HTTP/1.1 200 OK
	 * 	{
	 * 		"id": 665,
	 * 		"title": "Imóvel código 665, com 1 quarto e 1 banheiro",
	 * 		"price": 540000,
	 * 		"description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
	 * 		"x": 667,
	 * 		"y": 556,
	 * 		"beds": 1,
	 * 		"baths": 1,
	 * 		"provinces" : ["Ruja"],
	 * 		"squareMeters": 42
	 * 	}
	 * 
	 * @apiError PropertyNotFound The id of the Property was not found.
	 *
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 * 
	 */
	public PropertyResponse get(@Pattern(regexp = NUMERIC_PATTERN, message = "id must be numeric") @PathParam("id") final String id);

	/**
	 * @apiVersion 0.0.1
	 * @api {post} /v1/properties Request a property cretion
	 * @apiDescription Request a property creation
	 * @apiName CreateProperty
	 * @apiGroup Properties
	 * 
	 * @apiParam {PropertyRequest} Property Request.
	 * @apiParamExample {PropertyRequest} Property Request.
	 * 	{
	 * 		"title": "Imóvel código 665, com 1 quarto e 1 banheiro",
	 * 		"price": 540000,
	 * 		"description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
	 * 		"x": 667,
	 * 		"y": 556,
	 * 		"beds": 1,
	 * 		"baths": 1,
	 * 		"squareMeters": 42
	 * 	}
	 * 
	 * @apiSuccessExample Success-Response:
	 * 	HTTP/1.1 200 OK
	 * 	{
	 * 		"id": 665,
	 * 		"title": "Imóvel código 665, com 1 quarto e 1 banheiro",
	 * 		"price": 540000,
	 * 		"description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
	 * 		"x": 667,
	 * 		"y": 556,
	 * 		"beds": 1,
	 * 		"baths": 1,
	 * 		"provinces" : ["Ruja"],
	 * 		"squareMeters": 42
	 * 	}
	 * 
	 * @apiError PropertyInvalid The property request has something invalid
	 *
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 400 Bad Request
	 * 
	 */
	public PropertyResponse add(@NotNull @Valid PropertyRequest request);

	/**
	 * @apiVersion 0.0.1
	 * @api {get} /v1/properties?ax={integer}&ay={integer}&bx={integer}&by={integer} Request a list of properties given coordinates
	 * @apiDescription Request a list of properties given coordinates
	 * @apiName GetProperties
	 * @apiGroup Properties
	 * 
	 * @apiParam {Number} ax Upper left X.
	 * @apiParam {Number} ay Upper left Y.
	 * @apiParam {Number} bx Bottom right X.
	 * @apiParam {Number} by Bottom right Y.
	 * 
	 * @apiSuccessExample Success-Response:
	 * 	HTTP/1.1 200 OK
	 *	{
	 *		"foundProperties": 3,
	 *		"properties": [
	 *			{
	 *				"id": 34,
	 *				"title": "Imóvel código 34, com 4 quartos e 3 banheiros",
	 *				"price": 1250000,
	 *				"description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
	 *				"x": 999,
	 *				"y": 333,
	 *				"beds": 4,
	 *				"baths": 3,
	 *				"squareMeters": 237,
	 *				"provinces" : ["Scavy", "Gode"]
	 *			},
	 *			{"..."},
	 *			{"..."}
	 *		]
	 *	}
	 * 
	 * @apiError PropertiesNotFound No property was found.
	 *
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 * 
	 */
	public PropertiesResponse find(@NotNull @BeanParam @Valid final PropertyFilterParam propertyFilterParam);

}
