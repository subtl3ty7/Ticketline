import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {MyProfileWrapper} from '../../../my-profile-wrapper';
import {UserService} from '../../../../../services/user.service';
import {AuthService} from '../../../../../services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-delete-account',
  templateUrl: './delete-account.component.html',
  styleUrls: ['./delete-account.component.css']
})
export class DeleteAccountComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: MyProfileWrapper,
              public dialogRef: MatDialogRef<DeleteAccountComponent>,
              private userService: UserService,
              private authService: AuthService,
              private router: Router) { }

  public closeModal() {
    this.dialogRef.close();
  }

  public deleteAccount() {
    this.userService.delete(this.data.model).subscribe(() => {
      this.closeModal();
      this.authService.logoutUserDeleted();
      this.router.navigate(['/']);
    });
  }

}
