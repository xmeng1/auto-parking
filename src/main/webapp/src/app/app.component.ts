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
  constructor(
              private logger: NGXLogger,
              public dialog: MatDialog) {
  }

  title = 'auto-parking';
  ngOnInit() {
  }

  selectable = true;
  removable = false;
  fruitCtrl = new FormControl();
  selectedLang: string[] = [];

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
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
}
