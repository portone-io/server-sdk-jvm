package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * 통화 단위
 */
@Serializable
public enum class Currency {
  /**
   * 대한민국 원화
   */
  KRW,
  /**
   * 미국 달러
   */
  USD,
  /**
   * 일본 엔화
   */
  JPY,
  /**
   * UAE Dirham
   */
  AED,
  /**
   * Afghani
   */
  AFN,
  /**
   * Lek
   */
  ALL,
  /**
   * Armenian Dram
   */
  AMD,
  /**
   * Netherlands Antillean Guilder
   */
  ANG,
  /**
   * Kwanza
   */
  AOA,
  /**
   * Argentine Peso
   */
  ARS,
  /**
   * Australian Dollar
   */
  AUD,
  /**
   * Aruban Florin
   */
  AWG,
  /**
   * Azerbaijan Manat
   */
  AZN,
  /**
   * Convertible Mark
   */
  BAM,
  /**
   * Barbados Dollar
   */
  BBD,
  /**
   * Taka
   */
  BDT,
  /**
   * Bulgarian Lev
   */
  BGN,
  /**
   * Bahraini Dinar
   */
  BHD,
  /**
   * Burundi Franc
   */
  BIF,
  /**
   * Bermudian Dollar
   */
  BMD,
  /**
   * Brunei Dollar
   */
  BND,
  /**
   * Boliviano
   */
  BOB,
  /**
   * Mvdol
   */
  BOV,
  /**
   * Brazilian Real
   */
  BRL,
  /**
   * Bahamian Dollar
   */
  BSD,
  /**
   * Ngultrum
   */
  BTN,
  /**
   * Pula
   */
  BWP,
  /**
   * Belarusian Ruble
   */
  BYN,
  /**
   * Belize Dollar
   */
  BZD,
  /**
   * Canadian Dollar
   */
  CAD,
  /**
   * Congolese Franc
   */
  CDF,
  /**
   * WIR Euro
   */
  CHE,
  /**
   * Swiss Franc
   */
  CHF,
  /**
   * WIR Franc
   */
  CHW,
  /**
   * Unidad de Fomento
   */
  CLF,
  /**
   * Chilean Peso
   */
  CLP,
  /**
   * Yuan Renminbi
   */
  CNY,
  /**
   * Colombian Peso
   */
  COP,
  /**
   * Unidad de Valor Real
   */
  COU,
  /**
   * Costa Rican Colon
   */
  CRC,
  /**
   * Peso Convertible
   */
  CUC,
  /**
   * Cuban Peso
   */
  CUP,
  /**
   * Cabo Verde Escudo
   */
  CVE,
  /**
   * Czech Koruna
   */
  CZK,
  /**
   * Djibouti Franc
   */
  DJF,
  /**
   * Danish Krone
   */
  DKK,
  /**
   * Dominican Peso
   */
  DOP,
  /**
   * Algerian Dinar
   */
  DZD,
  /**
   * Egyptian Pound
   */
  EGP,
  /**
   * Nakfa
   */
  ERN,
  /**
   * Ethiopian Birr
   */
  ETB,
  /**
   * Euro
   */
  EUR,
  /**
   * Fiji Dollar
   */
  FJD,
  /**
   * Falkland Islands Pound
   */
  FKP,
  /**
   * Pound Sterling
   */
  GBP,
  /**
   * Lari
   */
  GEL,
  /**
   * Ghana Cedi
   */
  GHS,
  /**
   * Gibraltar Pound
   */
  GIP,
  /**
   * Dalasi
   */
  GMD,
  /**
   * Guinean Franc
   */
  GNF,
  /**
   * Quetzal
   */
  GTQ,
  /**
   * Guyana Dollar
   */
  GYD,
  /**
   * Hong Kong Dollar
   */
  HKD,
  /**
   * Lempira
   */
  HNL,
  /**
   * Kuna (Replaced by EUR)
   */
  HRK,
  /**
   * Gourde
   */
  HTG,
  /**
   * Forint
   */
  HUF,
  /**
   * Rupiah
   */
  IDR,
  /**
   * New Israeli Sheqel
   */
  ILS,
  /**
   * Indian Rupee
   */
  INR,
  /**
   * Iraqi Dinar
   */
  IQD,
  /**
   * Iranian Rial
   */
  IRR,
  /**
   * Iceland Krona
   */
  ISK,
  /**
   * Jamaican Dollar
   */
  JMD,
  /**
   * Jordanian Dinar
   */
  JOD,
  /**
   * Kenyan Shilling
   */
  KES,
  /**
   * Som
   */
  KGS,
  /**
   * Riel
   */
  KHR,
  /**
   * Comorian Franc 
   */
  KMF,
  /**
   * North Korean Won
   */
  KPW,
  /**
   * Kuwaiti Dinar
   */
  KWD,
  /**
   * Cayman Islands Dollar
   */
  KYD,
  /**
   * Tenge
   */
  KZT,
  /**
   * Lao Kip
   */
  LAK,
  /**
   * Lebanese Pound
   */
  LBP,
  /**
   * Sri Lanka Rupee
   */
  LKR,
  /**
   * Liberian Dollar
   */
  LRD,
  /**
   * Loti
   */
  LSL,
  /**
   * Libyan Dinar
   */
  LYD,
  /**
   * Moroccan Dirham
   */
  MAD,
  /**
   * Moldovan Leu
   */
  MDL,
  /**
   * Malagasy Ariary
   */
  MGA,
  /**
   * Denar
   */
  MKD,
  /**
   * Kyat
   */
  MMK,
  /**
   * Tugrik
   */
  MNT,
  /**
   * Pataca
   */
  MOP,
  /**
   * Ouguiya
   */
  MRU,
  /**
   * Mauritius Rupee
   */
  MUR,
  /**
   * Rufiyaa
   */
  MVR,
  /**
   * Malawi Kwacha
   */
  MWK,
  /**
   * Mexican Peso
   */
  MXN,
  /**
   * Mexican Unidad de Inversion (UDI)
   */
  MXV,
  /**
   * Malaysian Ringgit
   */
  MYR,
  /**
   * Mozambique Metical
   */
  MZN,
  /**
   * Namibia Dollar
   */
  NAD,
  /**
   * Naira
   */
  NGN,
  /**
   * Cordoba Oro
   */
  NIO,
  /**
   * Norwegian Krone
   */
  NOK,
  /**
   * Nepalese Rupee
   */
  NPR,
  /**
   * New Zealand Dollar
   */
  NZD,
  /**
   * Rial Omani
   */
  OMR,
  /**
   * Balboa
   */
  PAB,
  /**
   * Sol
   */
  PEN,
  /**
   * Kina
   */
  PGK,
  /**
   * Philippine Peso
   */
  PHP,
  /**
   * Pakistan Rupee
   */
  PKR,
  /**
   * Zloty
   */
  PLN,
  /**
   * Guarani
   */
  PYG,
  /**
   * Qatari Rial
   */
  QAR,
  /**
   * Romanian Leu
   */
  RON,
  /**
   * Serbian Dinar
   */
  RSD,
  /**
   * Russian Ruble
   */
  RUB,
  /**
   * Rwanda Franc
   */
  RWF,
  /**
   * Saudi Riyal
   */
  SAR,
  /**
   * Solomon Islands Dollar
   */
  SBD,
  /**
   * Seychelles Rupee
   */
  SCR,
  /**
   * Sudanese Pound
   */
  SDG,
  /**
   * Swedish Krona
   */
  SEK,
  /**
   * Singapore Dollar
   */
  SGD,
  /**
   * Saint Helena Pound
   */
  SHP,
  /**
   * Leone
   */
  SLE,
  /**
   * Leone
   */
  SLL,
  /**
   * Somali Shilling
   */
  SOS,
  /**
   * Surinam Dollar
   */
  SRD,
  /**
   * South Sudanese Pound
   */
  SSP,
  /**
   * Dobra
   */
  STN,
  /**
   * El Salvador Colon
   */
  SVC,
  /**
   * Syrian Pound
   */
  SYP,
  /**
   * Lilangeni
   */
  SZL,
  /**
   * Baht
   */
  THB,
  /**
   * Somoni
   */
  TJS,
  /**
   * Turkmenistan New Manat
   */
  TMT,
  /**
   * Tunisian Dinar
   */
  TND,
  /**
   * Pa’anga
   */
  TOP,
  /**
   * Turkish Lira
   */
  TRY,
  /**
   * Trinidad and Tobago Dollar
   */
  TTD,
  /**
   * New Taiwan Dollar
   */
  TWD,
  /**
   * Tanzanian Shilling
   */
  TZS,
  /**
   * Hryvnia
   */
  UAH,
  /**
   * Uganda Shilling
   */
  UGX,
  /**
   * US Dollar (Next day)
   */
  USN,
  /**
   * Uruguay Peso en Unidades Indexadas (UI)
   */
  UYI,
  /**
   * Peso Uruguayo
   */
  UYU,
  /**
   * Unidad Previsional
   */
  UYW,
  /**
   * Uzbekistan Sum
   */
  UZS,
  /**
   * Bolívar Soberano
   */
  VED,
  /**
   * Bolívar Soberano
   */
  VES,
  /**
   * Dong
   */
  VND,
  /**
   * Vatu
   */
  VUV,
  /**
   * Tala
   */
  WST,
  /**
   * CFA Franc BEAC
   */
  XAF,
  /**
   * Silver
   */
  XAG,
  /**
   * Gold
   */
  XAU,
  /**
   * Bond Markets Unit European Composite Unit (EURCO)
   */
  XBA,
  /**
   * Bond Markets Unit European Monetary Unit (E.M.U.-6)
   */
  XBB,
  /**
   * Bond Markets Unit European Unit of Account 9 (E.U.A.-9)
   */
  XBC,
  /**
   * Bond Markets Unit European Unit of Account 17 (E.U.A.-17)
   */
  XBD,
  /**
   * East Caribbean Dollar
   */
  XCD,
  /**
   * SDR (Special Drawing Right)
   */
  XDR,
  /**
   * CFA Franc BCEAO
   */
  XOF,
  /**
   * Palladium
   */
  XPD,
  /**
   * CFP Franc
   */
  XPF,
  /**
   * Platinum
   */
  XPT,
  /**
   * Sucre
   */
  XSU,
  /**
   * Codes specifically reserved for testing purposes
   */
  XTS,
  /**
   * ADB Unit of Account
   */
  XUA,
  /**
   * The codes assigned for transactions where no currency is involved
   */
  XXX,
  /**
   * Yemeni Rial
   */
  YER,
  /**
   * Rand
   */
  ZAR,
  /**
   * Zambian Kwacha
   */
  ZMW,
  /**
   * Zimbabwe Dollar
   */
  ZWL,
}
