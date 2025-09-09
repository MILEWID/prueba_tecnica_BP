import { Component, signal, ViewChild } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Sidebar } from './components/sidebar/sidebar';
import { Header } from './components/header/header';
import { ClientsCard } from './components/clients-card/clients-card';
import { Cuentas } from './components/cuentas/cuentas';
import { Movimientos } from './components/movimientos/movimientos';
import { Reportes } from './components/reportes/reportes';
import { TopBar } from './components/top-bar/top-bar';
import { Footer } from './components/footer/footer';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Sidebar, Header, ClientsCard, Cuentas, Movimientos, Reportes, TopBar, Footer, CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  @ViewChild(Sidebar) sidebar!: Sidebar;
  protected readonly title = signal('Clientes');
  protected readonly section = signal('clientes');

  onSectionChange(section: string) {
    this.section.set(section);
    const map: Record<string,string> = { clientes: 'Clientes', cuentas: 'Cuentas', movimientos: 'Movimientos', reportes: 'Reportes' };
    this.title.set(map[section] ?? 'App');
  }

  onMobileMenuToggle() {
    this.sidebar?.toggleMobileMenu();
  }
}
