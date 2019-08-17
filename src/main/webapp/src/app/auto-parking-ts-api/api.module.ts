import { NgModule, ModuleWithProviders, SkipSelf, Optional } from '@angular/core';
import { Configuration } from './configuration';
import { HttpClient } from '@angular/common/http';


import { AutoParkingControllerService } from './api/autoParkingController.service';
import { BasicErrorControllerService } from './api/basicErrorController.service';
import { HomeControllerService } from './api/homeController.service';
import { OperationHandlerService } from './api/operationHandler.service';
import { VersionControllerService } from './api/versionController.service';
import { WebMvcLinksHandlerService } from './api/webMvcLinksHandler.service';

@NgModule({
  imports:      [],
  declarations: [],
  exports:      [],
  providers: [
    AutoParkingControllerService,
    BasicErrorControllerService,
    HomeControllerService,
    OperationHandlerService,
    VersionControllerService,
    WebMvcLinksHandlerService ]
})
export class ApiModule {
    public static forRoot(configurationFactory: () => Configuration): ModuleWithProviders {
        return {
            ngModule: ApiModule,
            providers: [ { provide: Configuration, useFactory: configurationFactory } ]
        };
    }

    constructor( @Optional() @SkipSelf() parentModule: ApiModule,
                 @Optional() http: HttpClient) {
        if (parentModule) {
            throw new Error('ApiModule is already loaded. Import in your base AppModule only.');
        }
        if (!http) {
            throw new Error('You need to import the HttpClientModule in your AppModule! \n' +
            'See also https://github.com/angular/angular/issues/20575');
        }
    }
}
