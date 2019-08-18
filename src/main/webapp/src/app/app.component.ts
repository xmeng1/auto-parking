import {Component, Inject, OnChanges, OnInit, SimpleChanges, ViewChild, Pipe, PipeTransform} from '@angular/core';
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
import {
  VersionControllerService,
  ResultGitVersion,
  AutoParkingControllerService,
  CalculateRequestDto, CalculateRequest, CarParkLocation
} from './auto-parking-ts-api';
import CommandListEnum = CalculateRequest.CommandListEnum;
import HeadingStatusEnum = CarParkLocation.HeadingStatusEnum;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  numbersX: number[];
  numbersY: number[];

  // visualization for finding process
  cellStr: string[][];
  cellHeading: string[][];

  defaultBgColor = "lightblue";
  bgColor: string[][] = [];
  textColor: string[][] = [];

  constructor(
    private autoParkingControllerService: AutoParkingControllerService,
    private logger: NGXLogger,
    public dialog: MatDialog) {
    this.numbersX = Array(this.maxX).fill(1).map((x, i) => i + 1);
    this.numbersY = Array(this.maxY).fill(1).map((x, i) => i + 1);
    this.iniCellStr()
  }

  iniCellStr() {
    this.cellStr = [];
    this.cellHeading = [];

    for (let i = 0; i < this.maxY; i++) {
      this.cellStr[i] = [];
      this.cellHeading[i] = [];
      for (let j = 0; j < this.maxX; j++) {
        this.cellStr[i][j] = ""
        this.cellHeading[i][j] = ""
      }
    }
  }

  title = 'Auto-Parking System';
  maxX = 15;
  maxY = 15;
  x = 5;
  y = 5;
  commandStr = "FLFLFFRFFF";

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
    this.commandStr = this.commandStr.slice(0, -1);
  }

  onClearClick() {
    this.commandStr = "";
  }

  resultStr: String = "";

  onStartCalculateClick() {
    this.logger.debug("start to calculate");
    let dto: CalculateRequestDto = {
      commandListStr: this.commandStr,
      x: this.x,
      y: this.y
    };

    this.autoParkingControllerService.findLocationUsingPOST(dto).subscribe(
      res => {
        this.logger.debug("get the calculate result: ", res);
        if (res.error == 0) {
          this.resultStr = "Success! Final location: (" + res.result.x +
            "," + res.result.y +
            ") and facing " + res.result.headingStatus;
        }else {
          this.resultStr = "Failed! Code: " + res.error + "; Message: " + res.message + ".";
          if (res.result != undefined) {
            this.resultStr += "Actual final location: (" + res.result.x +
              "," + res.result.y +
              ") and facing " + res.result.headingStatus;
          }
        }
      }
    )
  }

  simulating: boolean = false;
  finish: boolean = false;

  currentCommand: CommandListEnum;
  nextCommand: CommandListEnum;
  currentHeading: HeadingStatusEnum = HeadingStatusEnum.North;
  commandsToRun: string = "";

  currentX = 0;
  currentY = 0;

  getCurrentHeadingImg(): string {
    //  ← → ↑ ↓
    switch (this.currentHeading) {
      case 'North':
        return "↑";
      case 'East':
        return "→";
      case 'South':
        return "↓";
      case 'West':
        return "←";
    }
  }


  onStartSimulate() {
    this.iniCellStr();
    this.currentX = this.x;
    this.currentY = this.y;
    this.currentHeading = HeadingStatusEnum.North;
    this.commandsToRun = this.commandStr;
    this.cellStr[this.currentX - 1][this.currentY - 1] = "⛩";
    this.cellHeading[this.currentX - 1][this.currentY - 1] = this.getCurrentHeadingImg();
    // this.getNextCommand();
    this.simulating = true;

  }

  getNextCommand() {
    this.currentCommand = CommandListEnum[this.commandsToRun.slice(0, 1)];
    this.commandsToRun = this.commandsToRun.slice(1, this.commandsToRun.length);
    this.nextCommand = CommandListEnum[this.commandsToRun.slice(0, 1)];
    this.logger.debug("debug: " + this.currentCommand + " " + this.commandsToRun);
  }

  onSimulateNext() {
    // get next string
    this.getNextCommand();
    // start to handle the command based on current facing
    switch (this.currentCommand) {
      case 'F':
        switch (this.currentHeading) {
          case 'North':
            if (this.currentX < this.maxX) {
              this.currentX++;
            }else{
              window.alert("Cannot move to North, current x: " + this.currentX + " has reach to max.")
            }
            break;
          case 'South':
            if (this.currentX > 1) {
              this.currentX--;
            }else{
              window.alert("Cannot move to South, current x: " + this.currentX + " has reach to min.")
            }
            break;
          case 'West':
            if (this.currentY > 1) {
              this.currentY--;
            }else{
              window.alert("Cannot move to West, current y: " + this.currentY + " has reach to min.")
            }
            break;
          case 'East':
            if (this.currentY < this.maxX) {
              this.currentY++;
            }else{
              window.alert("Cannot move to East, current Y: " + this.currentY + " has reach to max.")
            }
            break;
        }
        break;
      //  ← → ↑ ↓
      case 'L':
        switch (this.currentHeading) {
          case 'North':
            this.currentHeading = 'West';
            // this.cellStr[this.currentX - 1][this.currentY - 1] += (" " + "←");
            break;
          case 'South':
            this.currentHeading = 'East';
            // this.cellStr[this.currentX - 1][this.currentY - 1] += (" " + "→");
            break;
          case 'West':
            this.currentHeading = 'South';
            // this.cellStr[this.currentX - 1][this.currentY - 1] += (" " + "↓");
            break;
          case 'East':
            this.currentHeading = 'North';
            // this.cellStr[this.currentX - 1][this.currentY - 1] += (" " + "↑");
            break;
        }
        break;
      //  ← → ↑ ↓
      case 'R':
        switch (this.currentHeading) {
          case 'North':
            this.currentHeading = 'East';
            // this.cellStr[this.currentX - 1][this.currentY - 1] += (" " + "→");
            break;
          case 'South':
            this.currentHeading = 'West';
            // this.cellStr[this.currentX - 1][this.currentY - 1] += (" " + "←");
            break;
          case 'West':
            this.currentHeading = 'North';
            // this.cellStr[this.currentX - 1][this.currentY - 1] += (" " + "↑");
            break;
          case 'East':
            this.currentHeading = 'South';
            // this.cellStr[this.currentX - 1][this.currentY - 1] += (" " + "↓");
            break;
        }
        break;

    }
    //🚕 🚖  🚗 🚘
    this.cellStr[this.currentX - 1][this.currentY - 1] = "🚗";
    this.cellHeading[this.currentX - 1][this.currentY - 1] = this.getCurrentHeadingImg();
    if (this.currentCommand == undefined) {
      this.finish = true;
      this.cellStr[this.currentX - 1][this.currentY - 1] = "🏁";
    }
  }

  reset() {
    this.simulating = false;
    this.finish = false;
  }
}

///////////////////////
// About Dialog
export interface DialogData {
  animal: string;
  name: string;
}

@Component({
  selector: 'about-dialog',
  templateUrl: 'about-dialog.html',
})
export class AboutDialog {
  version = '';
  commitIdAbbrev = '';
  commitTime = '';
  buildTime = '';

  constructor(
    public dialogRef: MatDialogRef<AboutDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private versionControllerService: VersionControllerService,) {

    versionControllerService.getVersionUsingGET().subscribe((res: ResultGitVersion) => {
      this.version = res.result['buildVersion'];
      this.commitIdAbbrev = res.result['commitIdAbbrev'];
      this.commitTime = res.result['commitTime'];
      this.buildTime = res.result['buildTime'];
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}

@Pipe({name: 'reverse'})

export class ReversePipe implements PipeTransform {
  transform(value) {
    return value.slice().reverse();
  }
}
