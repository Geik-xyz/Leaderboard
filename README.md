> `This project discounted atm.`

# Leaderboard

Leaderboard basically sort players according to placeholder data of player. Then saves it to the SQL/MySQL and you can create menu/npc with that data.

> Note: ` Supports LeaderOS leaderboard for website.`

## Configuration file
<details>
  <summary>config.yml</summary>

    # MySQL üzerinde oluşacak tablo ismi
    TableName: siralama1
    # En iyi 10 sıralaması olsun mu? Fakat Setters'dan da açılması gerekmektedir.
    TopTen: false
    # En iyi 10 yazı formatı
    TopTen-Format: "&6{id} &a{player} &bPuanı: &c{value}"
    # En iyi 10 sıralamasının ne kadar sürede bir yenilenmesi gerektiğini belirler.
    # 1-2 Saatten az veriler girilmesi tavsiye edilmez, performansı direkt olarak etkileyebilir.
    # Devre dışı bırakmak için 0 yazın. Böylece sunucu kapanıp açıldıkça güncellenir.
    topTenUpdater: 90
    # NPC sıralama yapma özelliğini açıp kapatır.
    npcModifier: false

    # Mesaj dosyalarıdır.
    lang:
    noPerm: "&cBunun için yetkin yok!"
    reload: "&aConfig başarıyla yenilendi."
    topTenReload: "&aEn iyi 10 listesi yenilendi."
    wrongCommand: "&cHatalı kullanım. Komutlar: &a/lb reload <topten> | /lb reload | /lb create <data> <id>"

    # MySQL Tablosunda olacak veriler
    Setters:
    kill:
        value: '%statistic_player_kills%'
        topTen: false
        guiName: "&bÖldürme En iyi 10"
        topTenName: "&b{line}. {player}"
        topTenLore:
        - ""
        - " &8▪ &7Öldürme: &a{value}"
        - " &8▪ &7İsim: &a{player}"
        - " &8▪ &7Kategori: &6Öldürme"
        - " &8▪ &7Sıra: &e{line}"
    adaleveli:
        value: '%askyblock_level%'
        topTen: false
        guiName: "&bAda Seviyesi En iyi 10"
        topTenName: "&b{line}. {player}"
        topTenLore:
        - ""
        - " &8▪ &7Ada Seviyesi: &a{value}"
        - " &8▪ &7İsim: &a{player}"
        - " &8▪ &7Kategori: &6Ada Seviyesi"
        - " &8▪ &7Sıra: &e{line}"

    # Sitenin Mysql bilgilerinizi doldurun.
    Database:
    Host: 0.0.0.0
    DBName: leaderos_database
    Username: geyik
    Password: 123
    Port: 3306

    # Bilginiz yoksa aşağıdaki ayarları ellemeyin.
    Database-Connection-Settings:
    maxLifetime: 1400
    minimumConnections: 5
    maximumConnections: 20
    connectionTimeout: 30000
    useUnicode: true
    characterEncoding: UTF-8
    cachePrepStmts: true
    prepStmtCacheSize: 250
    prepStmtCacheSqlLimit: 2048
</details>

## Used Libraries

* [spigot-api (1.16-R0.5-SNAPSHOT)](https://hub.spigotmc.org/stash/projects/SPIGOT/repos/spigot/browse)
* [HikariCP](https://github.com/brettwooldridge/HikariCP)
* [placeholderapi](https://www.spigotmc.org/resources/placeholderapi.6245/)
* [VaultAPI](https://www.spigotmc.org/resources/vault.34315/)
* [SuperiorSkyblockAPI](https://bg-software.com/superiorskyblock/)
* [bentobox](https://www.spigotmc.org/resources/bentobox-bskyblock-acidisland-skygrid-caveblock-aoneblock-boxed.73261/)
* [json](https://www.json.org/json-en.html)

## Contributing

We welcome contributions from the community! If you would like to contribute, please follow these guidelines:

1. Fork the repository and clone it to your local machine.
2. Create a new branch for your feature or bug fix.
3. Make your changes, and ensure that your code is well-tested.
4. Create a pull request with a detailed description of your changes.

By contributing to this project, you agree to abide by the [Code of Conduct](CODE_OF_CONDUCT.md).