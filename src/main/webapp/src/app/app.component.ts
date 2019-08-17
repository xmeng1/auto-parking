import {Component, Inject, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {FormControl} from '@angular/forms';
import {NGXLogger} from "ngx-logger";
import localeCode from "iso-639-1";
import {Observable} from "rxjs";
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {ElementRef} from '@angular/core';
import {
  MatAutocompleteSelectedEvent,
  MatChipInputEvent,
  MatAutocomplete,
  MatDialogRef, MAT_DIALOG_DATA, MatDialog
} from '@angular/material';
import {map, startWith} from 'rxjs/operators';
import {MatSelectChange} from "@angular/material/typings/esm5/select";
import {Title} from "@angular/platform-browser";
import {HttpEvent, HttpEventType} from "@angular/common/http";
import {VersionControllerService, ResultGitVersion} from './auto-parking-ts-api';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  /*
  branch: "develop"
​​​​
buildTime: "2019-03-04T07:34:13+0000"
​​
buildVersion: "0.0.1"
​​
closestTagCommitCount: ""
​​
closestTagName: ""
​​
commitId: "06195d9a79883491a99332a96886a5322c78dfb2"
​​
commitIdAbbrev: "06195d9"
​​
commitIdDescribe: "06195d9-dirty"
​​
commitIdDescribeShort: "06195d9-dirty"
​​
commitMessageFull: "basic material design work"
​​
commitMessageShort: "basic material design work"
​​
commitTime: "2019-03-04T07:10:16+0000"
​​​​
dirty: "true"
​​​​
tags: ""
​​
totalCommitCount: "29"
  * */
  version = '';
  commitIdAbbrev = '';
  commitTime = '';
  buildTime = '';
  private numbersX: number[];
  private numbersY: number[];

  constructor(private versionControllerService: VersionControllerService,
              private logger: NGXLogger,
              public dialog: MatDialog) {
    versionControllerService.getVersionUsingGET().subscribe((res: ResultGitVersion) => {
      this.logger.debug("get the version result", res);
      this.version = res.result['buildVersion'];
      this.commitIdAbbrev = res.result['commitIdAbbrev'];
      this.commitTime = res.result['commitTime'];
      this.buildTime = res.result['buildTime'];
    });

    this.numbersX = Array(this.maxX).fill(1).map((x,i)=>i+1);
    this.numbersY = Array(this.maxY).fill(1).map((x,i)=>i+1);
  }

  title = 'Auto-Parking System';
  maxX = 15;
  maxY = 15;
  x = 0;
  y = 0;
  commandStr = "";
  ngOnInit() {
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(AboutDialog, {
      // height: '400px',
      // width: '600px',
      // data: {name: "xx", animal: "yy"}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  onFClick() {
    this.commandStr = this.commandStr + "F";
  }

  onLClick() {
    this.commandStr = this.commandStr + "L";
  }
  onRClick() {
    this.commandStr = this.commandStr + "R";
  }



  onDeleteClick() {
    this.commandStr  = this.commandStr.slice(0, -1);
  }

  onClearClick() {
    this.commandStr  = "";
  }
}

export interface DialogData {
  animal: string;
  name: string;
}

@Component({
  selector: 'about-dialog',
  templateUrl: 'about-dialog.html',
})
export class AboutDialog {
  constructor(
    public dialogRef: MatDialogRef<AboutDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
