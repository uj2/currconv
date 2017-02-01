INSERT INTO USER (email, first_name, last_name, password)
    VALUES ('joe@example.com', 'Joe', 'Simple',
    '$2a$10$.eMnm9R/E9xhOAzKhx0fqer9XKEPLwlmI9Ye.kJ/iYUrBeD4wdOKa');
INSERT INTO role(name) VALUES ('ROLE_USER');

INSERT INTO user_roles(user_id, roles_id) VALUES ((SELECT id FROM USER WHERE email='joe@example.com'),
(SELECT id FROM role WHERE name = 'ROLE_USER'));


INSERT INTO country(name) VALUES ('Afghanistan');
INSERT INTO country(name) VALUES ('Albania');
INSERT INTO country(name) VALUES ('Algeria');
INSERT INTO country(name) VALUES ('Andorra');
INSERT INTO country(name) VALUES ('Angola');
INSERT INTO country(name) VALUES ('Antigua and Barbuda');
INSERT INTO country(name) VALUES ('Argentina');
INSERT INTO country(name) VALUES ('Armenia');
INSERT INTO country(name) VALUES ('Australia');
INSERT INTO country(name) VALUES ('Austria');
INSERT INTO country(name) VALUES ('Azerbaijan');

INSERT INTO country(name) VALUES ('Bahamas');
INSERT INTO country(name) VALUES ('Bahrain');
INSERT INTO country(name) VALUES ('Bangladesh');
INSERT INTO country(name) VALUES ('Barbados');
INSERT INTO country(name) VALUES ('Belarus');
INSERT INTO country(name) VALUES ('Belgium');
INSERT INTO country(name) VALUES ('Belize');
INSERT INTO country(name) VALUES ('Benin');
INSERT INTO country(name) VALUES ('Bhutan');
INSERT INTO country(name) VALUES ('Bolivia');
INSERT INTO country(name) VALUES ('Bosnia and Herzegovina');
INSERT INTO country(name) VALUES ('Botswana');
INSERT INTO country(name) VALUES ('Brazil');
INSERT INTO country(name) VALUES ('Brunei');
INSERT INTO country(name) VALUES ('Bulgaria');
INSERT INTO country(name) VALUES ('Burkina Faso');
INSERT INTO country(name) VALUES ('Burundi');

INSERT INTO country(name) VALUES ('Cabo Verde');
INSERT INTO country(name) VALUES ('Cambodia');
INSERT INTO country(name) VALUES ('Cameroon');
INSERT INTO country(name) VALUES ('Canada');
INSERT INTO country(name) VALUES ('Central African Republic (CAR)');
INSERT INTO country(name) VALUES ('Chad');
INSERT INTO country(name) VALUES ('Chile');
INSERT INTO country(name) VALUES ('China');
INSERT INTO country(name) VALUES ('Colombia');
INSERT INTO country(name) VALUES ('Comoros');
INSERT INTO country(name) VALUES ('Democratic Republic of the Congo');
INSERT INTO country(name) VALUES ('Republic of the Congo');
INSERT INTO country(name) VALUES ('Costa Rica');
INSERT INTO country(name) VALUES ('Cote d''Ivoire');
INSERT INTO country(name) VALUES ('Croatia');
INSERT INTO country(name) VALUES ('Cuba');
INSERT INTO country(name) VALUES ('Cyprus');
INSERT INTO country(name) VALUES ('Czech Republic');

INSERT INTO country(name) VALUES ('Denmark');
INSERT INTO country(name) VALUES ('Djibouti');
INSERT INTO country(name) VALUES ('Dominica');
INSERT INTO country(name) VALUES ('Dominican Republic');

INSERT INTO country(name) VALUES ('Ecuador');
INSERT INTO country(name) VALUES ('Egypt');
INSERT INTO country(name) VALUES ('El Salvador');
INSERT INTO country(name) VALUES ('Equatorial Guinea');
INSERT INTO country(name) VALUES ('Eritrea');
INSERT INTO country(name) VALUES ('Estonia');
INSERT INTO country(name) VALUES ('Ethiopia');

INSERT INTO country(name) VALUES ('Fiji');
INSERT INTO country(name) VALUES ('Finland');
INSERT INTO country(name) VALUES ('France');

INSERT INTO country(name) VALUES ('Gabon');
INSERT INTO country(name) VALUES ('Gambia');
INSERT INTO country(name) VALUES ('Georgia');
INSERT INTO country(name) VALUES ('Germany');
INSERT INTO country(name) VALUES ('Ghana');
INSERT INTO country(name) VALUES ('Greece');
INSERT INTO country(name) VALUES ('Grenada');
INSERT INTO country(name) VALUES ('Guatemala');
INSERT INTO country(name) VALUES ('Guinea');
INSERT INTO country(name) VALUES ('Guinea-Bissau');
INSERT INTO country(name) VALUES ('Guyana');

INSERT INTO country(name) VALUES ('Haiti');
INSERT INTO country(name) VALUES ('Honduras');
INSERT INTO country(name) VALUES ('Hungary');

INSERT INTO country(name) VALUES ('Iceland');
INSERT INTO country(name) VALUES ('India');
INSERT INTO country(name) VALUES ('Indonesia');
INSERT INTO country(name) VALUES ('Iran');
INSERT INTO country(name) VALUES ('Iraq');
INSERT INTO country(name) VALUES ('Ireland');
INSERT INTO country(name) VALUES ('Israel');
INSERT INTO country(name) VALUES ('Italy');

INSERT INTO country(name) VALUES ('Jamaica');
INSERT INTO country(name) VALUES ('Japan');
INSERT INTO country(name) VALUES ('Jordan');

INSERT INTO country(name) VALUES ('Kazakhstan');
INSERT INTO country(name) VALUES ('Kenya');
INSERT INTO country(name) VALUES ('Kiribati');
INSERT INTO country(name) VALUES ('Kosovo');
INSERT INTO country(name) VALUES ('Kuwait');
INSERT INTO country(name) VALUES ('Kyrgyzstan');

INSERT INTO country(name) VALUES ('Laos');
INSERT INTO country(name) VALUES ('Latvia');
INSERT INTO country(name) VALUES ('Lebanon');
INSERT INTO country(name) VALUES ('Lesotho');
INSERT INTO country(name) VALUES ('Liberia');
INSERT INTO country(name) VALUES ('Libya');
INSERT INTO country(name) VALUES ('Liechtenstein');
INSERT INTO country(name) VALUES ('Lithuania');
INSERT INTO country(name) VALUES ('Luxembourg');

INSERT INTO country(name) VALUES ('Macedonia');
INSERT INTO country(name) VALUES ('Madagascar');
INSERT INTO country(name) VALUES ('Malawi');
INSERT INTO country(name) VALUES ('Malaysia');
INSERT INTO country(name) VALUES ('Maldives');
INSERT INTO country(name) VALUES ('Mali');
INSERT INTO country(name) VALUES ('Malta');
INSERT INTO country(name) VALUES ('Marshall Islands');
INSERT INTO country(name) VALUES ('Mauritania');
INSERT INTO country(name) VALUES ('Mauritius');
INSERT INTO country(name) VALUES ('Mexico');
INSERT INTO country(name) VALUES ('Micronesia');
INSERT INTO country(name) VALUES ('Moldova');
INSERT INTO country(name) VALUES ('Monaco');
INSERT INTO country(name) VALUES ('Mongolia');
INSERT INTO country(name) VALUES ('Montenegro');
INSERT INTO country(name) VALUES ('Morocco');
INSERT INTO country(name) VALUES ('Mozambique');
INSERT INTO country(name) VALUES ('Myanmar (Burma)');

INSERT INTO country(name) VALUES ('Namibia');
INSERT INTO country(name) VALUES ('Nauru');
INSERT INTO country(name) VALUES ('Nepal');
INSERT INTO country(name) VALUES ('Netherlands');
INSERT INTO country(name) VALUES ('New Zealand');
INSERT INTO country(name) VALUES ('Nicaragua');
INSERT INTO country(name) VALUES ('Niger');
INSERT INTO country(name) VALUES ('Nigeria');
INSERT INTO country(name) VALUES ('North Korea');
INSERT INTO country(name) VALUES ('Norway');

INSERT INTO country(name) VALUES ('Oman');

INSERT INTO country(name) VALUES ('Pakistan');
INSERT INTO country(name) VALUES ('Palau');
INSERT INTO country(name) VALUES ('Palestine');
INSERT INTO country(name) VALUES ('Panama');
INSERT INTO country(name) VALUES ('Papua New Guinea');
INSERT INTO country(name) VALUES ('Paraguay');
INSERT INTO country(name) VALUES ('Peru');
INSERT INTO country(name) VALUES ('Philippines');
INSERT INTO country(name) VALUES ('Poland');
INSERT INTO country(name) VALUES ('Portugal');

INSERT INTO country(name) VALUES ('Qatar');

INSERT INTO country(name) VALUES ('Romania');
INSERT INTO country(name) VALUES ('Russia');
INSERT INTO country(name) VALUES ('Rwanda');

INSERT INTO country(name) VALUES ('Saint Kitts and Nevis');
INSERT INTO country(name) VALUES ('Saint Lucia');
INSERT INTO country(name) VALUES ('Saint Vincent and the Grenadines');
INSERT INTO country(name) VALUES ('Samoa');
INSERT INTO country(name) VALUES ('San Marino');
INSERT INTO country(name) VALUES ('Sao Tome and Principe');
INSERT INTO country(name) VALUES ('Saudi Arabia');
INSERT INTO country(name) VALUES ('Senegal');
INSERT INTO country(name) VALUES ('Serbia');
INSERT INTO country(name) VALUES ('Seychelles');
INSERT INTO country(name) VALUES ('Sierra Leone');
INSERT INTO country(name) VALUES ('Singapore');
INSERT INTO country(name) VALUES ('Slovakia');
INSERT INTO country(name) VALUES ('Slovenia');
INSERT INTO country(name) VALUES ('Solomon Islands');
INSERT INTO country(name) VALUES ('Somalia');
INSERT INTO country(name) VALUES ('South Africa');
INSERT INTO country(name) VALUES ('South Korea');
INSERT INTO country(name) VALUES ('South Sudan');
INSERT INTO country(name) VALUES ('Spain');
INSERT INTO country(name) VALUES ('Sri Lanka');
INSERT INTO country(name) VALUES ('Sudan');
INSERT INTO country(name) VALUES ('Suriname');
INSERT INTO country(name) VALUES ('Swaziland');
INSERT INTO country(name) VALUES ('Sweden');
INSERT INTO country(name) VALUES ('Switzerland');
INSERT INTO country(name) VALUES ('Syria');

INSERT INTO country(name) VALUES ('Taiwan');
INSERT INTO country(name) VALUES ('Tajikistan');
INSERT INTO country(name) VALUES ('Tanzania');
INSERT INTO country(name) VALUES ('Thailand');
INSERT INTO country(name) VALUES ('Timor-Leste');
INSERT INTO country(name) VALUES ('Togo');
INSERT INTO country(name) VALUES ('Tonga');
INSERT INTO country(name) VALUES ('Trinidad and Tobago');
INSERT INTO country(name) VALUES ('Tunisia');
INSERT INTO country(name) VALUES ('Turkey');
INSERT INTO country(name) VALUES ('Turkmenistan');
INSERT INTO country(name) VALUES ('Tuvalu');
INSERT INTO country(name) VALUES ('Uganda');
INSERT INTO country(name) VALUES ('Ukraine');
INSERT INTO country(name) VALUES ('United Arab Emirates (UAE)');
INSERT INTO country(name) VALUES ('United Kingdom (UK)');
INSERT INTO country(name) VALUES ('United States of America (USA)');
INSERT INTO country(name) VALUES ('Uruguay');
INSERT INTO country(name) VALUES ('Uzbekistan');
INSERT INTO country(name) VALUES ('Vanuatu');
INSERT INTO country(name) VALUES ('Vatican City (Holy See)');
INSERT INTO country(name) VALUES ('Venezuela');
INSERT INTO country(name) VALUES ('Vietnam');
INSERT INTO country(name) VALUES ('Yemen');
INSERT INTO country(name) VALUES ('Zambia');
INSERT INTO country(name) VALUES ('Zimbabwe');