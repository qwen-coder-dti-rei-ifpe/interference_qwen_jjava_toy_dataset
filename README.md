# Toy Java Merge-Conflict Scenarios (S001 .. S010)

Ten small, self-contained scenarios. Each folder `S0xx/` has four snippets:

| file | role |
|---|---|
| `base.java`   | common ancestor version |
| `left.java`   | developer A's branch |
| `right.java`  | developer B's branch |
| `merge.java`  | the **result of a plausible "good-intentioned" merge** that compiles but contains **semantic conflicts / interference traps** |

Every scenario is designed so that the trap is invisible to a text-only merge tool:
the merged file builds cleanly, yet its behaviour matches **neither** branch.

| ID | Topic | Interference type (why the merge is a trap) |
|---|---|---|
| S001 | `DiscountCalculator` | Unit change (cents -> euros) + constant re-numbering + dropped fraud sentinel. Auto-merges, silently wrong money. |
| S002 | `OrderValidator` | Two error strategies (throw vs return-false) mixed; stricter regex lost; one method speaks two failure languages. |
| S003 | `UserRepository` | `Optional` API vs sentinel string coexist; `null` checks become dead code; stateful cache smuggled in. |
| S004 | `ListUtils` | "Union of both sides": overload added, report format dropped, `first()` throws while its sibling returns defaults. |
| S005 | `Counter` | `synchronized` + `AtomicInteger` union -> three different thread-safety disciplines in one class (worse than either branch). |
| S006 | `Config` | Asymmetric key normalisation: written lowercase, read exactly on one path -> silent default config in production. |
| S007 | `MoneyParser` | Contradictory meaning of `,` (decimal vs thousands) pipelined together -> charged 3.34 instead of 1,234.56. |
| S008 | `Cart` | Defensive copy (left) breaks the cached-total invariant (right) although neither touched the invariant. |
| S009 | `DateRange` | Global `[start,end)` convention changed by left, defended by right; merge keeps both -> off-by-one payroll/billing. |
| S010 | `Notifier` (interface) | Signature change + abstract method + compat default overload: compiles alone, breaks every external implementor at runtime/build. |

## Verified

* All 40 files compile with `javac 17` when copied to a file named after their public type
  (this is why they live as `base/left/right/merge.java` snippets rather than real `.java` units).
* Runtime proof of the traps (executed):
  * `S007.merge.parseCents("1,234.56")` -> `334` cents (expected `123456`)
  * `S007.merge.parseCents("12.5")`     -> `1205` cents (right branch produced `1250`)
  * `S001.merge.applyDiscount(10000, GOLD_TIER)` -> `90` (base produced `9000` cents)

## How to use them

For each scenario, ask: *"Is `merge.java` a correct integration of `left` and `right`?"*
The answer is always **no**, and the reason can only be found by reading `base.java`
(the hidden contract), which is exactly the blind spot of text-based merging tools.
