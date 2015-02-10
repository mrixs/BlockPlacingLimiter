# Block Placing Limiter
It's a Bukkit server plugin.

I've implemented a command which counts already placed blocks and limits.

It is `/bpl count` for blocks placed by you
and `/bpl count <player>` for blocks placed by `<player>`


Now I'll tell you how to configure my plugin.

**config.yml example:**
```YAML
enable: true
  blocks:
   blocks:
   - 1
   - 2
   - 25
   - 50
   number:
   - 3
   - 5
   - 0
   - 7
```
* You can enable/disable plugin by setiing "enable" in first string to "true" or "false"
* Next you can set limits for various blocks. In that example
  * block with ID = 1 has limit = 3,
  * block with ID = 2 has limit = 5,
  * block with ID = 25 has no limit (zero is unlimited),
  * block with ID = 50 has limit = 7,
  * other blocks are unlimited.

**!!! In this version it counts only blocks that has been placed or removed while plugin is enabled.**
