import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.scss'
})
export class Sidebar {
  @Input() active: string = 'clientes';
  @Output() sectionChange = new EventEmitter<string>();
  isMobileMenuOpen = false;
  select(section: string) {
    this.sectionChange.emit(section);
    this.closeMobileMenu(); 
  }
  toggleMobileMenu() {
    this.isMobileMenuOpen = !this.isMobileMenuOpen;
  }
  closeMobileMenu() {
    this.isMobileMenuOpen = false;
  }
}
