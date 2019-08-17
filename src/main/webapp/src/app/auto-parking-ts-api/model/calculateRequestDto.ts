/**
 * Api Documentation
 * Api Documentation
 *
 * OpenAPI spec version: 1.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


export interface CalculateRequestDto { 
    commandListStr?: string;
    headingStatus?: CalculateRequestDto.HeadingStatusEnum;
    ignoreUnknownCommand?: boolean;
    maxX?: number;
    maxY?: number;
    x?: number;
    y?: number;
}
export namespace CalculateRequestDto {
    export type HeadingStatusEnum = 'North' | 'South' | 'West' | 'East';
    export const HeadingStatusEnum = {
        North: 'North' as HeadingStatusEnum,
        South: 'South' as HeadingStatusEnum,
        West: 'West' as HeadingStatusEnum,
        East: 'East' as HeadingStatusEnum
    };
}
